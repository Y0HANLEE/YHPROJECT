package org.project.domain;

import java.util.List;

import lombok.Data;

@Data
public class IntroVO {
	private int boardtype;				//1:main, 2:intro, not null
	private String title_title;			//not null
	private String title_intro;
	private String map_title;
	private String map_intro;
	private String map_caption;
	private String map_address;
	private String map_addressdetail;
	private String intro;
	private List<IntroAttachVO> attachList;	
}
