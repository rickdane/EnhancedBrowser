package com.rickdane.springmodularizedproject.module.webgatherer.domain;

import com.rickdane.springmodularizedproject.domain.User;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Campaign;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson(deepSerialize = true)
@RooJpaActiveRecord(finders = { "findScrapersByUserOwner",
		"findScrapersByStatus", "findScrapersByStatusAndUserOwner" })
public class Scraper {
    
    public Scraper () {
        status = ProcessStatus.NOT_PROCESSED;
    }

	private String name;
    
    private String urlPrefix;
    
    private String urlPostfix;
    
    private int pageIncrementAmnt;
    
    private String keyword;
    
    private String baseDomainName;

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<User> userOwner = new HashSet<User>();

	@Enumerated
	private ProcessStatus status;

	@Enumerated
	private Type type;

	@ManyToOne(cascade = CascadeType.ALL)
	private Campaign campaign;

	@Transient
	private int fkCampaignId;

    private String javaScriptLinkIdentifier;

	public enum Type {

		CRAIGSLIST, INDEED, EMAIL_SCRAPE, URL_SCRAPE;
	}
}
