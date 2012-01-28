package com.rickdane.springmodularizedproject.module.userdata.domain;

import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findEmailTemplateSubjectsByEmailTemplateCategory" })
public class EmailTemplateSubject {

    private String subject;

    @Enumerated
    EmailTemplateStatus emailTemplateStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    private EmailTemplateCategory emailTemplateCategory;
}
