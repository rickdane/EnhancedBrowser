package com.rickdane.springmodularizedproject.domain;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findUserRolesByUserEntry" })
@RooJson(deepSerialize = true)
public class UserRole {

	@NotNull
	@ManyToOne
	private User userEntry;

	@NotNull
	@ManyToOne
	private Role roleEntry;
}
