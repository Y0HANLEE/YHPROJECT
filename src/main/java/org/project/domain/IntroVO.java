package org.project.domain;

import lombok.Data;

@Data
public class IntroVO {
	int boardtype;				//1:main, 2:intro, not null
	String title_title;			//not null
	String title_intro;
	String map_title;
	String map_intro;
	String map_caption;
	String map_address;
	String map_addressdetail;
	String intro;	
}
