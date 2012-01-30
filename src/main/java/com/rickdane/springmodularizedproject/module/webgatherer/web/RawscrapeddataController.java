package com.rickdane.springmodularizedproject.module.webgatherer.web;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.*;
import com.rickdane.springmodularizedproject.module.userdata.domain.EmailTemplateCategory;
import com.rickdane.springmodularizedproject.module.webgatherer.domain.Rawscrapeddata;
import com.rickdane.springmodularizedproject.module.webgatherer.domain.RawscrapeddataEmailScrapeAttempted;
import com.rickdane.springmodularizedproject.module.webgatherer.domain.Rawscrapeddatamigrationstatus;
import com.rickdane.springmodularizedproject.module.webgatherer.domain.Scraper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/rawscrapeddatas")
@Controller
@RooWebScaffold(path = "rawscrapeddatas", formBackingObject = Rawscrapeddata.class)
@RooWebJson(jsonObject = Rawscrapeddata.class)
public class RawscrapeddataController {

    @RequestMapping(params = "migrateForm", method = RequestMethod.GET)
    public String createMigrationForm(Model model) {
        RawscrapeddatamigrationForm form = new RawscrapeddatamigrationForm();
        model.addAttribute("Form", form);
        return "rawscrapeddatas/migrationForm";
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        Rawscrapeddata rawscrapeddata = Rawscrapeddata.fromJsonToRawscrapeddata(json);
        Scraper scraper = Scraper.findScraper(rawscrapeddata.getFkScraperId());
        Campaign campaign = scraper.getCampaign();
        rawscrapeddata.setCampaign(campaign);
        if (campaign.getCampaignEmailScrapeOptions() == CampaignEmailScrapeOptions.SCRAPE_EMAILS) {
            rawscrapeddata.setRawscrapeddataEmailScrapeAttempted(RawscrapeddataEmailScrapeAttempted.NOT_ATTEMPTED);
        }
        if (rawscrapeddata.getEmailAddress() != null) {
            rawscrapeddata.setRawscrapeddataEmailScrapeAttempted(RawscrapeddataEmailScrapeAttempted.ATTEMPTED);
        }
        rawscrapeddata.setRawscrapeddatamigrationstatus(Rawscrapeddatamigrationstatus.NOT_MIGRATED);
        rawscrapeddata.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Rawscrapeddata rawscrapeddata = Rawscrapeddata.fromJsonToRawscrapeddata(json);
        Scraper scraper = Scraper.findScraper(rawscrapeddata.getFkScraperId());
        Campaign campaign = scraper.getCampaign();
        rawscrapeddata.setCampaign(campaign);
        if (rawscrapeddata.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Rawscrapeddata rawscrapeddata, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, rawscrapeddata);
            return "rawscrapeddatas/create";
        }
        uiModel.asMap().clear();
        rawscrapeddata.persist();
        return "redirect:/rawscrapeddatas/" + encodeUrlPathSegment(rawscrapeddata.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/migrateAction", method = RequestMethod.POST)
    public String migrateAction(@Valid RawscrapeddatamigrationForm rawscrapeddatamigrationForm, BindingResult result, Model uiModel, HttpServletRequest request) {
        TypedQuery<Campaign> q = Campaign.findCampaignsByNameEquals(rawscrapeddatamigrationForm.getCampaignName());
        Campaign campaign = q.getSingleResult();
        TypedQuery<Rawscrapeddata> queryR = Rawscrapeddata.findRawscrapeddatasByRawscrapeddatamigrationstatusAndRawscrapeddataEmailScrapeAttempted(Rawscrapeddatamigrationstatus.NOT_MIGRATED, RawscrapeddataEmailScrapeAttempted.ATTEMPTED);
        List<Rawscrapeddata> rawscrapeddatas = queryR.getResultList();
        for (Rawscrapeddata curRawscrapeddata : rawscrapeddatas) {
            Campaign curCampaign = curRawscrapeddata.getCampaign();
            migrateRawScrapedData(curRawscrapeddata);
        }
        String pausse = "";
        uiModel.addAttribute("token", "All raw scraped data has been migrated");
        return "token";
    }

    @RequestMapping(value = "/retrieveUrlsAwaitingEmailScrape", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> retrieveUrlsAwaitingEmailScrape(@RequestBody String json) {
        TypedQuery<Campaign> queryC = Campaign.findCampaignsByCampaignEmailScrapeOptions(CampaignEmailScrapeOptions.SCRAPE_EMAILS);
        List<Campaign> campaigns = queryC.getResultList();
        String jsonString = null;
        for (Campaign curCampaign : campaigns) {
            TypedQuery<Rawscrapeddata> queryR = Rawscrapeddata.findRawscrapeddatasByCampaignAndRawscrapeddatamigrationstatusAndRawscrapeddataEmailScrapeAttempted(curCampaign, Rawscrapeddatamigrationstatus.NOT_MIGRATED, RawscrapeddataEmailScrapeAttempted.NOT_ATTEMPTED);
            List<Rawscrapeddata> rawscrapeddataList = queryR.getResultList();
            Rawscrapeddata rawscrapeddata = rawscrapeddataList.get(0);
            rawscrapeddata.setRawscrapeddataEmailScrapeAttempted(RawscrapeddataEmailScrapeAttempted.IN_PROGRESS);
            rawscrapeddata.persist();
            long id = 1;
            rawscrapeddata.setFkScraperId(id);
            jsonString = rawscrapeddata.toJson();
            break;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(jsonString, headers, HttpStatus.CREATED);
    }

    private void migrateRawScrapedData(Rawscrapeddata curRawscrapeddata) {
        EmailTemplateCategory emailTemplateCategory = curRawscrapeddata.getCampaign().getEmailTemplateCategories();
        Campaign curCampaign = curRawscrapeddata.getCampaign();
        String scrapedEmail = curRawscrapeddata.getEmailAddress();
        String domain = null;
        domain = getDomainNameFromUrl(curRawscrapeddata);
        if (domain != null) {
            TypedQuery<Website> queryW = Website.findWebsitesByDomainNameEquals(domain);
            Website website = null;
            if (queryW.getResultList().isEmpty()) {
                website = new Website();
                website.setDomainName(domain);
                website.setType(WebsiteType.SEARCH_ENGINE);
                // website.setCampaign(curCampaign);
                website.setEmailTemplateCategories(emailTemplateCategory);
                website.setWebsiteEmailSendStatus(WebsiteEmailSendStatus.NOT_IN_PROGRESS);
                website.persist();
            } else {

                website = queryW.getSingleResult();
            }

            Emailaddress newEmailAddress = null;
            if (curRawscrapeddata.getEmailAddress().contains("@")) {
                newEmailAddress = new Emailaddress();
                newEmailAddress.setEmail(scrapedEmail);
                newEmailAddress.setWebsite(website);
                newEmailAddress.persist();

            }

            TypedQuery<Emailaddress> queryEmail = Emailaddress.findEmailaddressesByWebsite(website);
            List<Emailaddress> matchingEmails = queryEmail.getResultList();
            if (matchingEmails.isEmpty() && newEmailAddress != null) {
                website.setEmailPrimary(newEmailAddress);
                website.persist();
            }

            Url url = new Url();
            url.setCampaign(curCampaign);
            url.setWebsite(website);
            url.setUrl(curRawscrapeddata.getUrl());
            url.persist();
            curRawscrapeddata.setRawscrapeddatamigrationstatus(Rawscrapeddatamigrationstatus.MIGRATED);
            curRawscrapeddata.persist();
        }
    }

    private String getDomainNameFromUrl(Rawscrapeddata curRawscrapeddata) {
        String url = null;
        try {
            url = new URL(curRawscrapeddata.getUrl()).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
