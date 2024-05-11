package org.project.domain.Board;

import org.project.domain.FileDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BoardAttachVO extends FileDTO {
	//private String uuid;		//pk, not null
	//private String uploadPath;	//not null
	//private String fileName;	//not null
	//private boolean fileType;	//default 'I'	
	
	private Long bno;			//fk
}
