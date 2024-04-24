package org.project.service;

import org.project.domain.IntroVO;
import org.springframework.stereotype.Service;

@Service
public interface IntroService {
	//조회
	public IntroVO read(int boardtype);
	//수정
	public int update(IntroVO intro);	
}
