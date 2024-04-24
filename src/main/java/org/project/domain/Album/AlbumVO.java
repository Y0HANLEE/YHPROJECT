package org.project.domain.Album;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AlbumVO {
	private Long ano;
	private String writer;
	private String content;	
	private Date regdate;	
	private Date updatedate;
	private int hit;
	private int replyCnt;
	private String location;
	private String title;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;	
	private List<AlbumAttachVO> attachList;
	private String boardType;
}