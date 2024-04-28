package org.project.domain.User;

import lombok.Data;

@Data
public class ProfileImageVO {
	private String uuid;		//pk, not null
	private String fileName;	//not null
	private String uploadPath;	//not null
	private boolean fileType;	//default 'I'
	private String userid;		//fk, not null
}
