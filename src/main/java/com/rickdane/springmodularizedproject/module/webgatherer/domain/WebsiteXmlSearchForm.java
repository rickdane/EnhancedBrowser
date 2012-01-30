package com.rickdane.springmodularizedproject.module.webgatherer.domain;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 1/29/12
 * Time: 9:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebsiteXmlSearchForm {

    private String keyword;

    private String location;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
