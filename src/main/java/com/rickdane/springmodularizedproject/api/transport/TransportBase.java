package com.rickdane.springmodularizedproject.api.transport;

import com.rickdane.springmodularizedproject.module.webgatherer.domain.Scraper;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class TransportBase {

	protected String userName;

	protected String passwordEncrytped;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswordEncrytped() {
		return passwordEncrytped;
	}

	public void setPasswordEncrytped(String passwordEncrytped) {
		this.passwordEncrytped = passwordEncrytped;
	}
}
