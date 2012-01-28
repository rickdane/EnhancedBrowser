package com.rickdane.springmodularizedproject.module.userdata.domain;

import com.rickdane.springmodularizedproject.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 1/13/12
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class EmailTemplateCategory {

    private String name;

    @Enumerated
    EmailTemplateStatus emailTemplateStatus;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<User>();

}
