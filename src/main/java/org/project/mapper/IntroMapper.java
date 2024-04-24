package org.project.mapper;

import org.project.domain.IntroVO;

public interface IntroMapper {
	//조회
	public IntroVO read(int boardtype);		
	//수정
	public int update(IntroVO intro); 	
}
