package org.project.mapper;


import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
public class User4Test {	
	@Setter(onMethod_= {@Autowired})
	private PasswordEncoder bcrypt;
	
	@Setter(onMethod_= {@Autowired})
	private DataSource data;
	
	/* 테스트 더미 생성 - 회원 */
	@Test
	public void testInsertUser() {
		String sql = "insert into users(userid, userpw, name) values (?,?,?)";
		
		for(int i=0; i<100; i++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = data.getConnection();
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(2, bcrypt.encode("pw"+i));
				
				if(i<80) {
					pstmt.setString(1, "mem"+i);
					pstmt.setString(3, "일반회원"+i);
				} else if(i<90) {
					pstmt.setString(1, "mgr"+i);
					pstmt.setString(3, "운영자"+i);
				} else {				
					pstmt.setString(1, "ad"+i);
					pstmt.setString(3, "관리자"+i);					
				}
				
				pstmt.executeUpdate();
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				if(pstmt != null) {
					try { pstmt.close(); } catch (Exception e) {}
				}				
				if(con != null) {
					try { con.close(); } catch (Exception e) { }
				}
			}
		}
	}
	
	/* 테스트 더미 생성 - 회원권한 */
	@Test
	public void testInsertAuth() {
		String sql = "insert into auth(userid, auth) values (?,?)";
		
		for(int i=0; i<100; i++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = data.getConnection();
				pstmt = con.prepareStatement(sql);				
				
				if(i<80) {
					pstmt.setString(1, "mem"+i);
					pstmt.setString(2, "ROLE_MEMBER");
				} else if(i<90) {
					pstmt.setString(1, "mgr"+i);
					pstmt.setString(2, "ROLE_MANAGER");
				} else {				
					pstmt.setString(1, "ad"+i);
					pstmt.setString(2, "ROLE_ADMIN");					
				}
				
				pstmt.executeUpdate();
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				if(pstmt != null) {
					try { pstmt.close(); } catch (Exception e) {}
				}				
				if(con != null) {
					try { con.close(); } catch (Exception e) { }
				}
			}
		}
	}	
}
