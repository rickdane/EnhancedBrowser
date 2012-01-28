package com.rickdane.springmodularizedproject.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findUsersByEmailAddress",
		"findUsersByActivationKeyAndEmailAddress" })
@RooJson(deepSerialize = true)
public class User {

	@NotNull
	@Size(min = 1)
	private String firstName;

	@NotNull
	@Size(min = 1)
	private String lastName;

	@NotNull
	@Column(unique = true)
	@Size(min = 1)
	private String emailAddress;

	@NotNull
	@Size(min = 1)
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date activationDate;

	private String activationKey;

	private Boolean enabled;

	private Boolean locked;

	public static User getAuthenticatedUser(String userName,
			String passwordEncrypted) {
		User user = new User();

		// user.s
		//
		// just for testing
		List<User> users = User.findAllUsers();
		//
		return users.get(0);

	}

}
