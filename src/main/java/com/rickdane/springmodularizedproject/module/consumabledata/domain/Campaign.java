package com.rickdane.springmodularizedproject.module.consumabledata.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.rickdane.springmodularizedproject.module.userdata.domain.EmailTemplateCategory;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson(deepSerialize = true)
@RooJpaActiveRecord(finders = { "findCampaignsByNameEquals",
        "findCampaignsByCampaignEmailScrapeOptions"})
public class Campaign {

    private String name;

    @Enumerated
    CampaignEmailScrapeOptions campaignEmailScrapeOptions;

    @Enumerated
    @NotNull
    CampaignType campaignType = CampaignType.REGULAR;

    @ManyToOne(cascade = CascadeType.ALL)
    private UrlBlockPattern campaign;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Datarecord> datarecords = new HashSet<Datarecord>();

    @ManyToOne(cascade = CascadeType.ALL)
    private EmailTemplateCategory emailTemplateCategories;

}
