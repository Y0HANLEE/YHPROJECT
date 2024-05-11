package org.project.domain.Album;

import org.project.domain.FileDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AlbumAttachVO extends FileDTO {
	//private String uuid;		//pk, not null
	//private String uploadPath;	//not null
	//private String fileName;	//not null
	//private boolean fileType;	//default 'I'
	
	private Long ano;			//fk
}
