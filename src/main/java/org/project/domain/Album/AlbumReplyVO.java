package org.project.domain.Album;

import java.util.Date;

import lombok.Data;

@Data
public class AlbumReplyVO {
	private Long rno;
	private Long ano;
	private String reply;
	private String replyer;
	private Date replyDate;
	private Date updateDate;
	private String boardType;
}
