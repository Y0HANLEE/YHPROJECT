package org.project.mapper.Intro;

import org.project.domain.IntroVO;

public interface IntroMapper {
	//���_���ʿ��� ����ϹǷ� ���񽺿��� ���x
	public void insert(IntroVO intro);	
	//��ȸ
	public IntroVO read(int boardtype);		
	//����
	public int update(IntroVO intro); 	
}
