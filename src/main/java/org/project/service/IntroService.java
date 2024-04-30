package org.project.service;

import java.util.List;

import org.project.domain.IntroAttachVO;
import org.project.domain.IntroVO;
import org.springframework.stereotype.Service;

@Service
public interface IntroService {
	//조회
	public IntroVO read(int boardtype);
	List<IntroAttachVO> attachList(int boardtype);	
	//수정
	public int update(IntroVO intro);
}
