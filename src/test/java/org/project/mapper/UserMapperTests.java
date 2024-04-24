package org.project.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Criteria;
import org.project.domain.Album.AlbumVO;
import org.project.domain.Board.BoardVO;
import org.project.domain.User.AuthVO;
import org.project.domain.User.UserVO;
import org.project.mapper.Album.AlbumMapper;
import org.project.mapper.Board.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper bmapper;
	
	@Setter(onMethod_ = @Autowired)
	private AlbumMapper amapper;	
	
	@Setter(onMethod_=@Autowired)
    private BCryptPasswordEncoder pwencode;
		
	@Setter(onMethod_=@Autowired)
	private DataSource ds;
		
	/*테스트더미 생성-users*/	
	@Test
	public void testInsertUser() {		
		String sql = "insert into users(userid, userpw, name) values (?,?,?)";
		for(int i=0; i<100; i++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {				
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(2, pwencode.encode("pw"+i));
				
				if(i<80) {
					pstmt.setString(1, "mem"+i);
					pstmt.setString(3, "회원"+i);					
				} else if(i<90) {
					pstmt.setString(1, "mgr"+i);
					pstmt.setString(3, "운영자"+i);					
				} else {
					pstmt.setString(1, "ad"+i);					
					pstmt.setString(3, "관리자"+i);					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) {try {pstmt.close();} catch (Exception e) {} }
				if(con != null) {try {con.close();} catch (Exception e) {} }
			}
			
		}
	}
	
	/*테스트더미 생성-auth*/	
	@Test
	public void testInsertAuth() {
		String sql = "insert into auth(userid, auth) values (?,?)";
		for(int i=0; i<100; i++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {				
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				
				if(i<80) {
					pstmt.setString(1, "mem"+i);
					pstmt.setString(2, "3");
				} else if(i<90) {
					pstmt.setString(1, "mgr"+i);
					pstmt.setString(2, "2");
				} else {
					pstmt.setString(1, "ad"+i);
					pstmt.setString(2, "1");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) {try {pstmt.close();} catch (Exception e) {} }
				if(con != null) {try {con.close();} catch (Exception e) {} }
			}
			
		}
	}
	
	@Test
	public void testRegister() {
		UserVO vo = new UserVO();
		AuthVO auth = new AuthVO();
		
		String rawPassword = "13"; // 사용자가 제공한 비밀번호
	    String encodedPassword = pwencode.encode(rawPassword);
	    
		vo.setUserid("13");
		vo.setUserpw(encodedPassword);
		vo.setName("13");
		vo.setGender("M");
		vo.setPhone("13");
		vo.setEmail("13");
		vo.setAddress("13");		
		
		auth.setUserid(vo.getUserid());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
        String dateString = "1221-11-11";
        try {
            Date date = sdf.parse(dateString);            
            vo.setBirth(date); 
        } catch (ParseException e) {          
        	e.printStackTrace();
        }       
		
		umapper.insertUser(vo);
		umapper.insertAuth(auth);
	}
	
	@Test
	public void testCheckId() {
		log.info("일치하는 아이디 "+umapper.checkId("14")+"건 발생");
	}
	
	@Test
	public void testUpdate() {
		UserVO vo = umapper.read("tester00");
		
		String rawPassword = "1234"; // 사용자가 제공한 비밀번호
	    String encodedPassword = pwencode.encode(rawPassword);
	    		
		vo.setUserpw(encodedPassword);		
		vo.setPhone("000-0000-0000");
		vo.setEmail("abcd@naver.com");
		vo.setAddress("광명시");		
		
		umapper.update(vo);		
	}
		
	@Test
	public void testRead() {
		UserVO user = umapper.read("qwerty");
		log.info(user);
		user.getAuthList().forEach(authVO -> log.info(authVO));
	}
	
	@Test
	public void testDelete() {
		log.info("--------------");
		umapper.delete("tester00");
		log.info("--------------");
	}	
	
	@Test
	public void testSearch() {
		Criteria c = new Criteria();		
		c.setKeyword("청주");
		c.setType("TCL");
		String u = "qwerty";		
		
		List<BoardVO> blist = umapper.boardList(c, u);
		List<AlbumVO> alist = umapper.albumList(c, u);
		alist.forEach(album -> log.info(album));
		blist.forEach(board -> log.info(board));		
	}
	
}
