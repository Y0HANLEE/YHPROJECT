package org.project.domain.Board;

import java.util.Date;

import lombok.Data;

@Data
public class BoardReplyVO {
	private Long rno;			//pk
	private Long bno;			//fk(board), not null
	private String reply;		//fk(users), not null
	private String replyer;		//not null
	private Date replyDate;
	private Date updateDate;
	private String boardType;	//default 1.1
}
