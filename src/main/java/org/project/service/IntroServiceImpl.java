package org.project.service;

import org.project.domain.IntroVO;
import org.project.mapper.IntroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
public class IntroServiceImpl implements IntroService{
	
	@Setter(onMethod_ = @Autowired)
	private IntroMapper imapper;
		
	/* 조회 */
	@Override	
	public IntroVO read(int boardtype) {	
		return imapper.read(boardtype);
	}
	
	/* 수정 */	
	@Override	
	public int update(IntroVO intro) {
		return imapper.update(intro);
	}
}
