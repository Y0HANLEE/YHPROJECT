package org.project.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class IntroAttachVO extends FileDTO {
	//private String uuid;		//pk, not null
	//private String uploadPath;	//not null
	//private String fileName;	//not null
	//private boolean fileType;	//default 'I'
	
	private int boardtype;		//fk
}
