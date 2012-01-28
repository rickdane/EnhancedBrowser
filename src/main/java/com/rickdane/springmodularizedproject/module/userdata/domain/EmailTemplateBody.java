package com.rickdane.springmodularizedproject.module.userdata.domain;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.Campaign;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findEmailTemplateBodysByEmailTemplateCategory" })
public class EmailTemplateBody {

    @Size(max = 10000)
    private String body;

    @Enumerated
    EmailTemplateStatus emailTemplateStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    private EmailTemplateCategory emailTemplateCategory;
}
