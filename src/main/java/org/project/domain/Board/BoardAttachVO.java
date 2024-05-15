package org.project.domain.Board;

import lombok.Data;

@Data
public class BoardAttachVO {
	private String uuid;		//pk, not null
	private String uploadPath;	//not null
	private String fileName;	//not null
	private boolean fileType;	//default 'I'	
	private Long bno;			//fk
}
