package com.rickdane.springmodularizedproject.module.webgatherer.domain;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.Campaign;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson(deepSerialize = true)
@RooJpaActiveRecord(finders = {
		"findRawscrapeddatasByRawscrapeddatamigrationstatus",
		"findRawscrapeddatasByRawscrapeddatamigrationstatusAndCampaign",
		"findRawscrapeddatasByCampaignAndRawscrapeddatamigrationstatusAndRawscrapeddataEmailScrapeAttempted",
		"findRawscrapeddatasByRawscrapeddatamigrationstatusAndRawscrapeddataEmailScrapeAttempted" })
public class Rawscrapeddata {

	private String url;

	private String emailAddress;

	private String text;

	@Transient
	private Long fkScraperId;

	@Enumerated
	private Rawscrapeddatamigrationstatus rawscrapeddatamigrationstatus;

	@Enumerated
	private RawscrapeddataEmailScrapeAttempted rawscrapeddataEmailScrapeAttempted;

    @NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Campaign campaign;
}
