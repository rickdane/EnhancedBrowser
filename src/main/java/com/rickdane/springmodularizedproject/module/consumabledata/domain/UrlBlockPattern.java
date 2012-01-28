package com.rickdane.springmodularizedproject.module.consumabledata.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findUrlBlockPatternsByPatternLike" })
public class UrlBlockPattern {

    private String pattern;

    @ManyToOne(cascade = CascadeType.ALL)
    private Campaign campaign;
}
