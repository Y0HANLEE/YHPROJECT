package org.project.domain.Board;

import java.util.Date;

import lombok.Data;

@Data
public class BoardReplyVO {
	private Long rno;
	private Long bno;
	private String reply;
	private String replyer;
	private Date replyDate;
	private Date updateDate;
	private String boardType;
}
