package org.project.service;

import org.project.domain.IntroVO;
import org.springframework.stereotype.Service;

@Service
public interface IntroService {
	//��ȸ
	public IntroVO read(int boardtype);
	//����
	public int update(IntroVO intro);	
}
