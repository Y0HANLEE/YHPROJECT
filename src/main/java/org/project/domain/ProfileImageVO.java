package org.project.domain;

import lombok.Data;

@Data
public class ProfileImageVO {
	private String userid;
	private String fileName;
	private String uploadPath;
	private String fileType;	
}
