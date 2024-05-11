package org.project.domain.User;

import org.project.domain.FileDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProfileImageVO extends FileDTO {
	//private String uuid;		//pk, not null
	//private String fileName;	//not null
	//private String uploadPath;	//not null
	//private boolean fileType;	//default 'I'
	
	private String userid;		//fk, not null
}
