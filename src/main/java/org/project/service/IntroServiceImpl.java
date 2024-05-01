package org.project.service;

import java.util.List;

import org.project.domain.IntroAttachVO;
import org.project.domain.IntroVO;
import org.project.mapper.Intro.IntroAttachMapper;
import org.project.mapper.Intro.IntroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
public class IntroServiceImpl implements IntroService{
	
	@Setter(onMethod_ = @Autowired)
	private IntroMapper imapper;
	
	@Setter(onMethod_ = @Autowired)
	private IntroAttachMapper amapper;
	
	/* ��ȸ */
	@Override	
	public IntroVO read(int boardtype) {	
		return imapper.read(boardtype);
	}
	
	/* ÷������ ��� ��ȸ */
	@Override
	public List<IntroAttachVO> attachList(int boardtype){
		return amapper.findByBoard(boardtype);
	}
	
	/* ���� */	
	@Override	
	public int update(IntroVO intro) {
		int result = imapper.update(intro);
		amapper.deleteAll(intro.getBoardtype());
		
		if(result==1 && intro.getAttachList()!=null && intro.getAttachList().size()>0) {
			intro.getAttachList().forEach(attach -> {
				attach.setBoardtype(intro.getBoardtype());
				amapper.insert(attach);
			});
		}			
		return result;
	}
}
