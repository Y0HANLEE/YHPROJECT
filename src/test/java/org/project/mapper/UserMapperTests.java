package org.project.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Criteria;
import org.project.domain.MyCriteria;
import org.project.domain.Album.AlbumReplyVO;
import org.project.domain.Album.AlbumVO;
import org.project.domain.User.AuthVO;
import org.project.domain.User.UserVO;
import org.project.mapper.User.UserMapper;
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
	
		
	/* ȸ�� ��� �׽�Ʈ */
	@Test
	public void testInsertUser() {
		UserVO user = new UserVO();	    
		user.setUserid("13");
		user.setUserpw("13"); //�׽�Ʈ�� ���� ���ڵ� ����
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
	
	/* ȸ�� ���� �ο� �׽�Ʈ */
	@Test
	public void testInsertAuth() {
		AuthVO auth = new AuthVO(); 
		auth.setUserid("admin");			
		umapper.insertAuth(auth);
	}
	
	/* ȸ�� ���� ��ȸ �׽�Ʈ */
	@Test
	public void testGetUserInfo() {				
		log.info(umapper.read("admin"));		
	}
	
	/* ȸ�� ���� ��ȸ �׽�Ʈ */
	@Test
	public void testGetUserAuth() {
		umapper.readAuth("13");
	}	
	
	/* ���̵� �ߺ� �׽�Ʈ */
	@Test
	public void testCheckId() {
		log.info("��ġ�ϴ� ���̵� "+umapper.checkId("13")+"�� �߻�");
	}
	
	/* ���̵� ã�� �׽�Ʈ */
	@Test
	public void testFindId() {		
		log.info("��ġ�ϴ� ���̵� : "+umapper.findId("13", "13"));
	}
	
	/* ���� �� �Խñ� ã�� boardtype 1:�Ϲ�, 2:���� */
	@Test
	public void testFindMyBoardList() {
		MyCriteria cri = new MyCriteria();
		cri.setPageNum(1);
		cri.setAmount(100000);
		cri.setKeyword("");
		cri.setType("");
		cri.setUserid("admin");
		cri.setBoardType("2");
		//List<BoardVO> list = umapper.boardList(cri);
		List<AlbumVO> list = umapper.albumList(cri);
		list.forEach(no->log.info(no));
	}	

	/* ���� �� ��� ã�� boardtype 1.1:�Ϲ�, 2.1:���� */
	@Test
	public void testFindMyBoardReplyList() {
		MyCriteria cri = new MyCriteria();
		cri.setPageNum(1);
		cri.setAmount(100000);
		cri.setKeyword("");
		cri.setType("");
		cri.setUserid("admin");
		cri.setBoardType("2.1");		
		//List<BoardReplyVO> list = umapper.boardReplyList(cri);
		List<AlbumReplyVO> list = umapper.albumReplyList(cri);
		list.forEach(no->log.info(no));
	}
		
	/* �Խñ�,��� ī��Ʈ */
	@Test
	public void testGetCount() {
		String id = "admin";
		log.info("-----------------------[board]"+umapper.getBoardCnt(id));
		log.info("-----------------------[album]"+umapper.getAlbumCnt(id));
		log.info("-----------------------[boardReply]"+umapper.getBoardReplyCnt(id));
		log.info("-----------------------[albumReply]"+umapper.getAlbumReplyCnt(id));
	}
		
	/* ȸ������ ���� �׽�Ʈ */
	@Test
	public void testUpdateUserInfo() {
		UserVO user = umapper.read("admin");		
		user.setPhone("010-1234-4321");
		umapper.update(user);		
	}
	
	/* ȸ�� ��й�ȣ ���� �׽�Ʈ */
	@Test
	public void testUpdatePw() {
		umapper.updatePw("1222", "admin", "1234");		
	}
	
	/* ��й�ȣ �ʱ�ȭ �׽�Ʈ */
	@Test
	public void testRenewalPw() {
		String ranPw = RandomStringUtils.randomAlphanumeric(8);
		umapper.renewalPw(ranPw, "13", "13");
	}
	
	/* ȸ������ ���� �׽�Ʈ */
	@Test
	public void testDeleteUser() {
		umapper.delete("13");		
	}
	
	/* ȸ�� ���� ���� �׽�Ʈ */
	@Test
	public void testDeleteUserAuth() {
		umapper.deleteAuth("13");	
	}
	
	/* ȸ�� ���� ���� �׽�Ʈ */
	@Test
	public void testDeleteUserSession() {
		umapper.deleteSession("13");		
	}
	
	/* ������ : ȸ�� ��� ��ȸ �׽�Ʈ */	
	@Test
	public void testGetUserList() {
		Criteria cri = new Criteria();
		cri.setAmount(10);
		cri.setPageNum(1);		
		List<UserVO> members = umapper.getUserList(cri);
		members.forEach(user -> log.info(user));
	}
	
	/* ������ : �� ȸ���� �ľ� */
	@Test
	public void getTotalUserCnt() {
		Criteria cri = new Criteria();
		cri.setAmount(10);
		cri.setPageNum(1);
		umapper.getTotalUser(cri);
	}
	
	/* ������ : ȸ����� ���� */
	@Test
	public void updateUserAuth() {
		umapper.updateAuth("ROLE_ADMIN", "admin");
	}	
}
