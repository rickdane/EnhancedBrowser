package com.rickdane.springmodularizedproject.module.webgatherer.domain;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.Campaign;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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

    private String radius;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Campaign campaign;

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

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
