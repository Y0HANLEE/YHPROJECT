package org.project.domain.Album;

import lombok.Data;

@Data
public class AlbumAttachVO {
	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean fileType;
	private Long ano;	
}
