package com.rickdane.springmodularizedproject.api.transport;

import org.springframework.roo.addon.json.RooJson;

@RooJson
public class WebGathererJobJsonTransport extends TransportBase {

	private String jobType;

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
}
