package org.project.mapper;

import org.project.domain.IntroVO;

public interface IntroMapper {
	//��ȸ
	public IntroVO read(int boardtype);		
	//����
	public int update(IntroVO intro); 	
}
