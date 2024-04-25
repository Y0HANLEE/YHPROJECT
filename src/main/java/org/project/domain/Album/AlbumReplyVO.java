package org.project.domain.Album;

import java.util.Date;

import lombok.Data;

@Data
public class AlbumReplyVO {
	private Long rno;			//pk
	private Long ano;			//fk(album), not null
	private String reply;		//not null
	private String replyer;		//fk(users), not null
	private Date replyDate;
	private Date updateDate;
	private String boardType;
}
