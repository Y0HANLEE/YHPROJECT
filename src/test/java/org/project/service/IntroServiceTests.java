package org.project.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.IntroVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class IntroServiceTests {

	@Setter(onMethod_ = @Autowired)
	private IntroService iservice;
	
	/* 메인화면 내용 조회 */
	@Test	
	public void getIntroTest() {		
		log.info("[IntroServiceTest] HomePage----------------"+iservice.read(1));
		log.info("[IntroServiceTest] IntroPage---------------"+iservice.read(2));
	}
	
	/* 메인화면 내용 수정 */
	@Test
	public void updateIntroTest() {
		IntroVO home = iservice.read(1);
		home.setTitle_intro("");
		home.setMap_address(""); 
		home.setMap_addressdetail("");
		home.setMap_caption(""); 
		home.setMap_intro(""); 
		home.setMap_title("");
		home.setIntro("update HomePage Intro_Service");
		iservice.update(home);
		log.info("[IntroServiceTest] HomePage----------------"+iservice.read(1));
		
		
		IntroVO intro = iservice.read(2);		
		
		intro.setTitle_intro(""); 
		intro.setMap_address("");
		intro.setMap_addressdetail(""); 
		intro.setMap_caption("");
		intro.setMap_intro(""); 
		intro.setMap_title("");
		 
		intro.setIntro("update IntroPage Intro_Service");
		iservice.update(intro);		
		log.info("[IntroServiceTest] IntroPage---------------"+iservice.read(2));
	}
}

