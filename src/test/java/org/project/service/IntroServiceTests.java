package org.project.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.IntroVO;
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
public class IntroServiceTests {

	@Setter(onMethod_ = @Autowired)
	private IntroService iservice;
	
	/* ����ȭ�� ���� ��ȸ */
	@Test	
	public void getIntroTest() {		
		log.info("[IntroServiceTest] HomePage----------------"+iservice.read(1));
		log.info("[IntroServiceTest] IntroPage---------------"+iservice.read(2));
	}
	
	/* ����ȭ�� ���� ���� */
	@Test
	public void updateIntroTest() {
		IntroVO home = iservice.read(1);
		home.setTScript("");
		home.setAddress(""); 
		home.setAddressdetail("");
		home.setCaption(""); 
		home.setMScript(""); 
		home.setMap("");
		home.setIntro("update HomePage Intro_Service");
		iservice.update(home);
		log.info("[IntroServiceTest] HomePage----------------"+iservice.read(1));
		
		
		IntroVO intro = iservice.read(2);		
		
		intro.setTScript("");
		intro.setAddress(""); 
		intro.setAddressdetail("");
		intro.setCaption(""); 
		intro.setMScript(""); 
		intro.setMap("");
		intro.setIntro("update IntroPage Intro_Service");
		iservice.update(intro);		
		log.info("[IntroServiceTest] IntroPage---------------"+iservice.read(2));
	}
}

