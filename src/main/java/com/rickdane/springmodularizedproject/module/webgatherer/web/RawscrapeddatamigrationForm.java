package com.rickdane.springmodularizedproject.module.webgatherer.web;

import javax.validation.constraints.NotNull;

public class RawscrapeddatamigrationForm {

	@NotNull
	private String campaignName;

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

}
