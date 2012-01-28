package com.rickdane.springmodularizedproject.module.consumabledata.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
@RooWebJson(jsonObject = com.rickdane.springmodularizedproject.module.consumabledata.domain.ReceivedEmail.class)
public class ReceivedEmail {

    private String fromAddress;

    private String toAddress;

    @Size(max = 10000)
    private String subject;

    private String content;

}
