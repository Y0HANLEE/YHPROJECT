package org.project.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Criteria;
import org.project.domain.MyCriteria;
import org.project.domain.User.AuthVO;
import org.project.domain.User.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class UserServiceTests {

	@Setter(onMethod_ = @Autowired)
	private UserService uservice;
	
	/* 회원 등록 */
	@Test
	public void testJoin() {
		UserVO user = new UserVO();
		/*
		ProfileImageVO profile = new ProfileImageVO();		
		profile.setFileName("");
		profile.setFileType("");
		profile.setUploadPath("");
		profile.setUuid("");
		profile.setUserid("test00");*/
		user.setUserid("test00");
		user.setUserpw("test00");
		user.setName("tester");
		user.setGender("W");
		user.setEmail("kevinyh@naver.com");
		user.setPhone("000-000-0000");
		user.setAddress("11");
		user.setAddressDetail("11");
		user.setZonecode("11111");
		//user.getProfileImg();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");		
        String dateString = "1990/11/21";
        try {
            Date date = sdf.parse(dateString);            
            user.setBirth(date); 
        } catch (ParseException e) {          
        	e.printStackTrace();
        }
		
		AuthVO auth = new AuthVO();
		auth.setUserid("test00");
		auth.setAuth("ROLE_USER");
		uservice.join(user, auth);
	}
	
	/* 회원 조회 */
	@Test
	public void testReadUserInfo() {		
		uservice.read("qwerty");		
	}	
	
	/* 회원 등급 조회 */
	@Test
	public void testReadUserAuth() {
		uservice.readAuth("qwerty"); 
	}
	
	/* 회원 아이디 중복확인 */
	@Test
	public void testCheckUserId() {
		uservice.checkId("qwerty"); 
	}
	
	/* 회원 아이디 조회 */
	@Test
	public void testFindUserId() {
		uservice.findId("이요한", "kevinyh@naver.com"); // 아이디 찾기
	}
	
	/* 내가 쓴 게시글/댓글 조회 */
	@Test
	public void testFindUserContents() {
		MyCriteria cri = new MyCriteria();
		cri.setUserid("qwerty");
		cri.setAmount(1000);
		//cri.setBoardType("1");
		//uservice.findBoardByUserid(cri); // 일반게시글
		//cri.setBoardType("2");
		//uservice.findAlbumByUserid(cri); // 사진게시글
		//cri.setBoardType("1.1");
		//uservice.findBoardReplyByUserid(cri); // 일반댓글
		cri.setBoardType("2.1");
		uservice.findAlbumReplyByUserid(cri); // 사진댓글
	}
	
	/* 내가 쓴 게시글/댓글 개수 조회 */
	@Test
	public void testGetUserContentsCnt() {
		uservice.getBoardCnt("qwerty");
		uservice.getAlbumCnt("qwerty");
		uservice.getBoardReplyCnt("qwerty");
		uservice.getAlbumReplyCnt("qwerty");
	}
	
	/* 회원 프로필 조회 */
	@Test
	public void testGetProfile() {
		uservice.getProfileByUserid("qwerty"); //프로필사진조회		
	}
	
	/* 회원 정보수정 */
	@Test
	public void testUpdateUserInfo() {
		UserVO user = uservice.read("test00");
		user.setPhone("010-0000-0000");
		uservice.modify(user);
		log.info("----------------------"+uservice.read("test00"));
	}
	
	/* 회원 비밀번호 수정 */
	@Test
	public void testUpdatePw() {
		uservice.updatePw("test01", "test00", "test00"); 
	}
	
	/* 회원 비밀번호 초기화 */
	@Test
	public void testRenewalPw() {		
		uservice.renewalPw("qfkh18", "test00", "kevinyh@naver.com");
	}
	
	/* 회원 삭제 */
	@Test
	public void testDeleteUser() {
		uservice.remove("test00");		
	}
	
	/* 회원 등록 */
	@Test
	public void testGetUserList() {
		uservice.getUserList(new Criteria(1,100));				
	}
	
	/* 총 회원 수 카운트 */
	@Test
	public void testGetTotalUserCnt() {
		uservice.getTotalUser(new Criteria(1,100));
	}
	
	/* 회원 등급 조정 */
	@Test
	public void testUpadteAuth() {
		uservice.updateAuth("ROLE_ADMIN", "test00");
		log.info("------------------------------------------"+uservice.readAuth("test00"));
	}
	
}



