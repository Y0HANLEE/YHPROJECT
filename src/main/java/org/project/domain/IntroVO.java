package org.project.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class IntroVO {
	private int boardtype; //1:main, 2:intro, not null
	private String title;  //not null
	private String tscript;	
	private String map;
	private String mscript;
	private String caption;
	private String address;
	private String addressdetail;
	private String intro;
	private Date regdate;
	private Date updateDate;
	private List<IntroAttachVO> attachList;	
}
