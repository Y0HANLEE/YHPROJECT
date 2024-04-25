package org.project.domain;

import lombok.Data;

@Data
public class ProfileImageVO {
	private String uuid;		//pk, not null
	private String fileName;	//not null
	private String uploadPath;	//not null
	private String fileType;	//default 'I'
	private String userid;		//fk, not null
}
