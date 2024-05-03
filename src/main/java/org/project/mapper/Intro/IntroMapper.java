package org.project.mapper.Intro;

import org.project.domain.IntroVO;

public interface IntroMapper {
	//등록_최초에만 등록하므로 서비스에선 사용x
	public void insert(IntroVO intro);	
	//조회
	public IntroVO read(int boardtype);		
	//수정
	public int update(IntroVO intro); 	
}
