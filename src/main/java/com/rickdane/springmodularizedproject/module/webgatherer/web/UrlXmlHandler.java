package com.rickdane.springmodularizedproject.module.webgatherer.web;

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

    List<String> urls = new ArrayList<String>();

    private CharArrayWriter contents = new CharArrayWriter();

    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) {
        contents.reset();

    }

    public void endElement(String namespaceURI,
                           String localName,
                           String qName) {

        if (qName.equalsIgnoreCase("url")) {
            String url = contents.toString();
            urls.add(url);
        }
    }

    public void characters(char[] ch, int start, int length) {

        contents.write(ch, start, length);
    }

    public List<String> getUrls() {
        return urls;
    }

}




