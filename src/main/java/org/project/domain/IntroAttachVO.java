package org.project.domain;

import lombok.Data;

@Data
public class IntroAttachVO {
	private String uuid;		//pk, not null
	private String uploadPath;	//not null
	private String fileName;	//not null
	private boolean fileType;	//default 'I'
	private int boardtype;		//fk
}
