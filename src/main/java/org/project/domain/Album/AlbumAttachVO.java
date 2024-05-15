package org.project.domain.Album;

import lombok.Data;

@Data
public class AlbumAttachVO {
	private String uuid;		//pk, not null
	private String uploadPath;	//not null
	private String fileName;	//not null
	private boolean fileType;	//default 'I'
	private Long ano;			//fk
}
