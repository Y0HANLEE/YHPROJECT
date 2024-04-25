package org.project.domain.User;

import lombok.Data;

@Data
public class AuthVO {
	private String userid;	// fk, not null
	private String auth;	// not null
}
