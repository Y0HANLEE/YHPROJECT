package org.project.domain.Board;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private Long bno;			// pk
	private String title;		// not null
	private String content;		// not null
	private String writer;		// fk, not null
	private Date regdate;			
	private Date updateDate;	
	private int replyCnt;
	private int hit;
	private List<BoardAttachVO> attachList;
	private String boardType;	// default 1
	
	private int next; //다음
	private int prev; //이전
	private String nexttitle; //다음글제목
	private String prevtitle; //이전글제목
}
