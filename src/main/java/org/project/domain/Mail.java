package org.project.domain;

import lombok.Data;

@Data
public class Mail {
	private String fromUser;
	private String toUser;
	private String title;
	private String content;
}
