package org.project.domain.Album;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AlbumVO {
	private Long ano;			//pk
	private String writer;		//fk, not null
	private String content;		//not null
	private Date regdate;		
	private Date updatedate;
	private int hit;
	private int replyCnt;
	private String location;
	private String title;		//not null
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;	
	private List<AlbumAttachVO> attachList;
	private String boardType;	//default 2
	
	private int next; //다음
	private int prev; //이전
	private String nexttitle; //다음글제목
	private String prevtitle; //이전글제목
}