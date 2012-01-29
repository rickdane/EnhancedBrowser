package com.rickdane.springmodularizedproject.module.consumabledata.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = {"findUrlsByUrlEqualsAndCampaign", "findUrlsByUrlStatusAndCampaign"})
public class Url {

    private String url;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Campaign campaign;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Website website;

    @Enumerated
    @NotNull
    private UrlStatus urlStatus = UrlStatus.ACTIVE;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar dateLastPostedTo;


    @Transactional
    public void persist() {
        if (url != null && campaign != null && website != null) {
            TypedQuery<Url> queryU = Url.findUrlsByUrlEqualsAndCampaign(url, campaign);
            if (queryU.getResultList().size() >= 2) {
                return;
            }
            if (!queryU.getResultList().isEmpty()) {
                Url urlCheck = queryU.getSingleResult();
                if (urlCheck.getId() != getId()) {
                    //only allow it to persist if there is already a url that has the same id of this one (update)
                    return;
                }
            }

            TypedQuery<UrlBlockPattern> QueryUB = UrlBlockPattern.findUrlBlockPatternsByPatternLike(website.getDomainName());
            if (QueryUB.getResultList().isEmpty()) {
                if (this.entityManager == null) this.entityManager = entityManager();
                this.entityManager.persist(this);
            }
        }
    }
}
