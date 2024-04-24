package org.project.domain.Board;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;	
	private int replyCnt;
	private int hit;
	private List<BoardAttachVO> attachList;
	private String boardType;
}
