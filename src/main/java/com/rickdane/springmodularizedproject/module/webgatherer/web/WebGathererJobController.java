package com.rickdane.springmodularizedproject.module.webgatherer.web;

import com.rickdane.springmodularizedproject.domain.User;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Campaign;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Url;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Website;
import com.rickdane.springmodularizedproject.module.webgatherer.domain.*;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/webgathererjobs")
public class WebGathererJobController {

    @RequestMapping(value = "/getPendingJobToLaunch", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> launchJob(@RequestBody String json) {
        // WebGathererJobJsonTransport webGathererJJT =
        // WebGathererJobJsonTransport.fromJsonToWebGathererJobJsonTransport(json);

        HttpHeaders headers = new HttpHeaders();

        // User validatedUser =
        // User.getAuthenticatedUser(webGathererJJT.getUserName(),
        // webGathererJJT.getPasswordEncrytped());
        // if (validatedUser == null) {
        // headers.add("Content-Type", "application/json");
        // return new ResponseEntity<String>(headers, HttpStatus.FORBIDDEN);
        // }

        // TODO: account for concurrency and find by user not just find all
        // unprocessed

        try {

            TypedQuery<Scraper> findScraper = Scraper
                    .findScrapersByStatus(ProcessStatus.NOT_PROCESSED);

            Scraper scraper = findScraper.getSingleResult();

            String jsonScraper = scraper.toJson();

            scraper.setStatus(ProcessStatus.IN_PROGRESS);
            scraper.persist();

            headers.add("Content-Type", "application/json");
            return new ResponseEntity<String>(jsonScraper, HttpStatus.CREATED);
        } catch (Exception e) {
            Scraper scraper = new Scraper();
            scraper.setName("it didn't work");

            String jsonScraper = scraper.toJson();
            headers.add("Content-Type", "application/json");
            return new ResponseEntity<String>(jsonScraper, headers,
                    HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(params = "urlXmlSearchForm", method = RequestMethod.GET)
    public String createMigrationForm(Model model) {
        WebsiteXmlSearchForm form = new WebsiteXmlSearchForm();
        model.addAttribute("Form", form);
        List<Campaign> campaigns = Campaign.findAllCampaigns();
        populateCreateForm(model);

        return "urls/xmlSearchForm";
    }


    void populateCreateForm(Model uiModel) {
        uiModel.addAttribute("campaigns", Campaign.findAllCampaigns());
    }

    @RequestMapping(value = "/urlXmlSearch", method = RequestMethod.POST)
    public String urlXmlSearch(@Valid WebsiteXmlSearchForm websiteXmlSearchForm, BindingResult result, Model uiModel, HttpServletRequest request) {

        //TODO this is very raw and is hard-coded now for using Indeed.com's XML api, will need to be re-worked going forward

        final String apiKey = "6795409185280638";
        int limit = 40;

        int pages = 10;

        //TODO: make this selectable by user, Campaign & Website are just hard-coded for initial testing
        Website website = Website.findAllWebsites().get(0);

       Campaign campaign = websiteXmlSearchForm.getCampaign();

        String radius = websiteXmlSearchForm.getRadius();
        if (radius == null || radius.equals("")) {
            radius = "10";
        }

        String keyword = websiteXmlSearchForm.getKeyword() + "+" + websiteXmlSearchForm.getLocation();

        int start = 1;

        for (int i = 1; i <= pages; i++) {
            String xmlSearchString = "http://api.indeed.com/ads/apisearch?publisher=" + apiKey + ""
                    + "&q=" + keyword + "&sort=&radius=" + radius
                    + "&st=&jt=&start=" + start + "&limit=" + limit + "&v=2";

            String response = getUrl(xmlSearchString);

            List<String> urls = parseUrlsFromXml(response);

            persistUrls(urls, campaign,website);

            start = limit + start;
        }

        return "token";
    }

    private void persistUrls(List<String> urls, Campaign campaign, Website website) {

        for (String urlStr : urls) {
            //persist the url
            Url url = new Url();
            url.setCampaign(campaign);
            url.setWebsite(website);
            url.setUrl(urlStr);
            url.persist();
        }
    }

    /**
     * TODO: this is very raw, needs to be re-worked into something solid at some point
     *
     * @param xml
     * @return
     */
    private List<String> parseUrlsFromXml(String xml) {

        List<String> retList = null;

        UrlXmlHandler urlXmlHandler = new UrlXmlHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();

        javax.xml.parsers.SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        InputStream xmlInputStream = null;

        try {
            xmlInputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }

        try {
            saxParser.parse(xmlInputStream, urlXmlHandler);
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        retList = urlXmlHandler.getUrls();
        return retList;

    }

    /**
     * TODO: obviously this is just-adhoc out of haste, refactor this to proper util lib
     *
     * @return
     */
    private String getUrl(String urlStr) {
        URL url;
        InputStream is = null;
        DataInputStream dis;
        String line;
        StringBuilder strBld = new StringBuilder();

        try {
            url = new URL(urlStr);
            is = url.openStream();  // throws an IOException
            dis = new DataInputStream(new BufferedInputStream(is));

            while ((line = dis.readLine()) != null) {
                strBld.append((line));
            }
        } catch (Exception e) {
        } finally {
            try {
                try {
                    is.close();
                } catch (Exception e) {

                }
            } catch (Exception e) {

            }
        }
        return strBld.toString();
    }
}
