package com.rickdane.springmodularizedproject.module.webgatherer.web;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.Url;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 1/29/12
 * Time: 10:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class UrlXmlHandler extends DefaultHandler {

    List<Url> urls = new ArrayList<Url>();
    Url url;

    private CharArrayWriter contents = new CharArrayWriter();

    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) {
        contents.reset();

        if (qName.equalsIgnoreCase("result")) {
            url = new Url();
            urls.add(url);
        }

    }

    public void endElement(String namespaceURI,
                           String localName,
                           String qName) {
        
        if (qName.equalsIgnoreCase("url")) {
            url.setUrl(contents.toString());
        }

        if (qName.equalsIgnoreCase("jobtitle")) {
            url.setTitle(contents.toString());
        }

        if (qName.equalsIgnoreCase("company")) {
            url.setCompany(contents.toString());
        }

        if (qName.equalsIgnoreCase("snippet")) {
            url.setPreview(contents.toString());
        }

    }

    public void characters(char[] ch, int start, int length) {

        contents.write(ch, start, length);
    }

    public List<Url> getUrls() {
        return urls;
    }

}




