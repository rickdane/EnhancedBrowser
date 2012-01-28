package com.rickdane.springmodularizedproject.domain;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson(deepSerialize = true)
public class Role {

	@NotNull
	@Column(unique = true)
	@Size(min = 1)
	private String roleName;

	@NotNull
	@Size(max = 200)
	private String roleDescription;
}
