package home.starterpack;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.IntroVO;
import org.project.domain.User.AuthVO;
import org.project.domain.User.ProfileImageVO;
import org.project.domain.User.UserVO;
import org.project.mapper.Intro.IntroMapper;
import org.project.mapper.User.ProfileImageMapper;
import org.project.mapper.User.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class AdminStarterPackTests {
	@Setter(onMethod_ = @Autowired)
	private UserMapper u;
	@Setter(onMethod_ = @Autowired)
	private ProfileImageMapper p;
	@Setter(onMethod_ = @Autowired)
	private IntroMapper i;
	@Setter(onMethod_ = @Autowired)
	BCryptPasswordEncoder bcrypt;
		
	/* 회원 등록 테스트 */
	@Test
	public void testAdminStarterPack() {
		//1회만 실시_admin계정, home/intro화면 글 등록 > 클라이언트는 수정만 가능.
		//등록정보 객체 생성
		UserVO user = new UserVO();
		AuthVO auth = new AuthVO();
		IntroVO home = new IntroVO();
		IntroVO intro = new IntroVO();
		ProfileImageVO profile = new ProfileImageVO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//관리자계정 등록할 정보		
		String id = "admin";
		String password = bcrypt.encode("1234");
		String name = "관리자";
		String email = "kevinyh18@gmail.com";
		String gender = "M"; //"M":남성 "F":여성
		String birth = "1900-01-01"; 
		//메인/소개화면 등록할 정보
		String tt = "제목입니다. 수정할때도 반드시 기입해주세요";
		String ti = "페이지 소개글입니다.";
		String mt = "지도 제목입니다.";
		String mi = "지도 소개글입니다.";
		String mc = "지도 마커 표시글";
		String ad = "클릭하시면 주소검색이 실행됩니다.";
		String add = "상세주소입니다";
		String hi = "페이지 하단부에 들어갈 내용입니다. 연락처 혹은 필요하신 내용을 기입해주세요";
		String ii = "적고 싶은 내용이 있다면 적어주세요.";		
		//관리자등록세팅
		user.setUserid(id);
		auth.setUserid(id);			
		profile.setUserid(id);
		user.setUserpw(password);
		user.setName(name);
		user.setEmail(email);
		user.setGender(gender);
		try { Date date = sdf.parse(birth); user.setBirth(date); } catch (ParseException e) {e.printStackTrace();}		
		profile.setUuid("ed87212c-4e79-4813-be6c-8c73ac58ac33");
		profile.setFileName("Default-Profile.png");
		profile.setFileType(true);
		profile.setUploadPath("2024\\04\\27");
		//메인화면내용세팅
		home.setTitle_title(tt);
		home.setTitle_intro(ti);
		home.setMap_title(mt);
		home.setMap_intro(mi);
		home.setMap_caption(mc);
		home.setMap_address(ad); 
		home.setMap_addressdetail(add); 
		home.setIntro(hi); 
		home.setBoardtype(1);
		//소개화면내용세팅
		intro.setTitle_title(tt);
		intro.setTitle_intro(ti);
		intro.setMap_title(mt);
		intro.setMap_intro(mi);
		intro.setMap_caption(mc);
		intro.setMap_address(ad); 
		intro.setMap_addressdetail(add); 
		intro.setIntro(ii); 
		intro.setBoardtype(2);
		//등록
		u.insertUser(user); 				//회원등록
		u.insertAuth(auth); 				//관리자ID_기본권한등록
		u.updateAuth("ROLE_ADMIN", id); 	//관리자ID_관리자등급으로 조정
		p.insert(profile); 					//기본프로필 등록
		i.insert(home); 					//메인화면 등록
		i.insert(intro); 					//소개화면 등록
		//확인
		log.info("[Admin Insert Account]--------- "+u.read(id));
		log.info("[Admin Insert Auth]------------ "+u.readAuth(id));
		log.info("[Admin Insert Profile]--------- "+p.getProfileByUserid(id));
		log.info("[Admin Insert HomePage]-------- "+i.read(1));
		log.info("[Admin Insert IntroPage]------- "+i.read(2));
	}	
}
