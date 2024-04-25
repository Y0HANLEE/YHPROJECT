package org.project.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
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
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class UserMapperTests {
	@Setter(onMethod_ = @Autowired)
	private UserMapper umapper;
		
	/* 회원 등록 테스트 */
	@Test
	public void testInsertUser() {
		UserVO user = new UserVO();	    
		user.setUserid("13");
		user.setUserpw("13"); //테스트를 위해 인코딩 생략
		user.setName("13");
		user.setGender("M");
		user.setPhone("13");
		user.setEmail("13");
		user.setZonecode("13");
		user.setAddress("13");
		user.setAddressDetail("13");		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
        String dateString = "1221-11-11";
        try {
            Date date = sdf.parse(dateString);            
            user.setBirth(date); 
        } catch (ParseException e) {          
        	e.printStackTrace();
        }       
		
		umapper.insertUser(user);
	}
	
	/* 회원 권한 부여 테스트 */
	@Test
	public void testInsertAuth() {
		AuthVO auth = new AuthVO(); 
		auth.setUserid("13");
		auth.setAuth("ROLE_USER");		
		umapper.insertAuth(auth);
	}
	
	/* 회원 정보 조회 테스트 */
	@Test
	public void testGetUserInfo() {
		umapper.read("13");		
	}
	
	/* 회원 권한 조회 테스트 */
	@Test
	public void testGetUserAuth() {
		umapper.readAuth("13");
	}	
	
	/* 아이디 중복 테스트 */
	@Test
	public void testCheckId() {
		log.info("일치하는 아이디 "+umapper.checkId("13")+"건 발생");
	}
	
	/* 아이디 찾기 테스트 */
	@Test
	public void testFindId() {		
		log.info("일치하는 아이디 : "+umapper.findId("13", "13"));
	}
	
	/* 내가 쓴 일반게시판 게시글 찾기 */
	@Test
	public void testFindMyBoardList() {
		MyCriteria cri = new MyCriteria();
		cri.setPageNum(1);
		cri.setAmount(100000);
		cri.setKeyword("");
		cri.setType("");
		cri.setUserid("qwerty");
		cri.setBoardType("1");
		umapper.boardList(cri);
	}
	
	/* 내가 쓴 사진게시판 게시글 찾기 */
	@Test
	public void testFindMyAlbumList() {
		MyCriteria cri = new MyCriteria();
		cri.setPageNum(1);
		cri.setAmount(100000);
		cri.setKeyword("");
		cri.setType("");
		cri.setUserid("admin1");
		cri.setBoardType("2");
		umapper.albumList(cri);
	}
	
	/* 내가 쓴 일반게시판 댓글 찾기 */
	@Test
	public void testFindMyBoardReplyList() {
		MyCriteria cri = new MyCriteria();
		cri.setPageNum(1);
		cri.setAmount(100000);
		cri.setKeyword("");
		cri.setType("");
		cri.setUserid("qwerty");
		cri.setBoardType("1.1");
		umapper.boardReplyList(cri);
	}
	
	/* 내가 쓴 사진게시판 댓글 찾기 */
	@Test	
	public void testFindMyAlbumReplyList() {
		MyCriteria cri = new MyCriteria();
		cri.setPageNum(1);
		cri.setAmount(100000);
		cri.setKeyword("");
		cri.setType("");
		cri.setUserid("qwerty");
		cri.setBoardType("2.1");
		umapper.albumReplyList(cri);
	}
	
	/* 게시글,댓글 카운트 */
	@Test
	public void testGetCount() {
		log.info("-----------------------[board]"+umapper.getBoardCnt("qwerty"));
		log.info("-----------------------[album]"+umapper.getAlbumCnt("qwerty"));
		log.info("-----------------------[boardReply]"+umapper.getBoardReplyCnt("qwerty"));
		log.info("-----------------------[albumReply]"+umapper.getAlbumReplyCnt("qwerty"));
	}
		
	/* 회원정보 수정 테스트 */
	@Test
	public void testUpdateUserInfo() {
		UserVO user = umapper.read("13");		
		user.setPhone("010-1234-4321");
		umapper.update(user);		
	}
	
	/* 회원 비밀번호 수정 테스트 */
	@Test
	public void testUpdatePw() {
		umapper.updatePw("12", "13", "13");		
	}
	
	/* 비밀번호 초기화 테스트 */
	@Test
	public void testRenewalPw() {
		String ranPw = RandomStringUtils.randomAlphanumeric(8);
		umapper.renewalPw(ranPw, "13", "13");
	}
	
	/* 회원정보 삭제 테스트 */
	@Test
	public void testDeleteUser() {
		umapper.delete("13");		
	}
	
	/* 회원 권한 삭제 테스트 */
	@Test
	public void testDeleteUserAuth() {
		umapper.deleteAuth("13");	
	}
	
	/* 회원 세션 삭제 테스트 */
	@Test
	public void testDeleteUserSession() {
		umapper.deleteSession("13");		
	}
	
	/* 관리자 : 회원 목록 조회 테스트 */	
	@Test
	public void testGetUserList() {
		Criteria cri = new Criteria();
		cri.setAmount(10);
		cri.setPageNum(1);
		umapper.getUserList(cri);
	}
	
	/* 관리자 : 총 회원수 파악 */
	@Test
	public void getTotalUserCnt() {
		Criteria cri = new Criteria();
		cri.setAmount(10);
		cri.setPageNum(1);
		umapper.getTotalUser(cri);
	}
	
	/* 관리자 : 회원등급 조정 */
	@Test
	public void updateUserAuth() {
		umapper.updateAuth("ROLE_ADMIN", "qwerty");
	}	
}
