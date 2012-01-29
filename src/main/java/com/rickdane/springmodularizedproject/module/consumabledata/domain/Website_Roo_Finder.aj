// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.rickdane.springmodularizedproject.module.consumabledata.domain;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.Website;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.WebsiteEmailSendStatus;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Website_Roo_Finder {
    
    public static TypedQuery<Website> Website.findWebsitesByDateLastSentEmailIsNullAndWebsiteEmailSendStatus(WebsiteEmailSendStatus websiteEmailSendStatus) {
        if (websiteEmailSendStatus == null) throw new IllegalArgumentException("The websiteEmailSendStatus argument is required");
        EntityManager em = Website.entityManager();
        TypedQuery<Website> q = em.createQuery("SELECT o FROM Website AS o WHERE o.dateLastSentEmail IS NULL  AND o.websiteEmailSendStatus = :websiteEmailSendStatus", Website.class);
        q.setParameter("websiteEmailSendStatus", websiteEmailSendStatus);
        return q;
    }
    
    public static TypedQuery<Website> Website.findWebsitesByDomainNameEquals(String domainName) {
        if (domainName == null || domainName.length() == 0) throw new IllegalArgumentException("The domainName argument is required");
        EntityManager em = Website.entityManager();
        TypedQuery<Website> q = em.createQuery("SELECT o FROM Website AS o WHERE o.domainName = :domainName", Website.class);
        q.setParameter("domainName", domainName);
        return q;
    }
    
}