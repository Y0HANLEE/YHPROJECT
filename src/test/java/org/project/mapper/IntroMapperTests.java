package org.project.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.IntroVO;
import org.project.mapper.Intro.IntroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
@AllArgsConstructor
public class IntroMapperTests {

	@Setter(onMethod_ = @Autowired)
	private IntroMapper imapper;
			
	/* �Ұ�ȭ��-�Ұ��� ��ȸ */
	@Test
	public void getIntro() {
		log.info("[IntroMapperTest] HomePage------------------"+imapper.read(1));
		log.info("[IntroMapperTest] IntroPage-----------------"+imapper.read(2));
	}
	
	/* �Ұ�ȭ�� ���� */
	@Test
	public void updateIntro() {
		IntroVO home = imapper.read(1); //DB���� null�� ��������� jdbc���� ������ ��ĭ����� ���� ����. test�� ���� ��ĭ ����.
		home.setTitle_intro("");
		home.setMap_address(""); 
		home.setMap_addressdetail("");
		home.setMap_caption(""); 
		home.setMap_intro(""); 
		home.setMap_title("");
		home.setIntro("update HomePage Intro");
		imapper.update(home);
		log.info("[IntroMapperTest] HomePage------------------"+imapper.read(1));		
		
		IntroVO intro = imapper.read(2);
		intro.setTitle_intro("");
		intro.setMap_address(""); 
		intro.setMap_addressdetail("");
		intro.setMap_caption(""); 
		intro.setMap_intro(""); 
		intro.setMap_title("");
		intro.setIntro("update IntroPage Intro");
		imapper.update(intro);		
		log.info("[IntroMapperTest] IntroPage------------------"+imapper.read(2));
	}
}

