package org.project.controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class SecurityControllerTests {
	
	@Setter(onMethod_= {@Autowired})
	private PasswordEncoder bcrypt;
	
	@Setter(onMethod_= {@Autowired})
	private DataSource data;
	
	@Test
	public void testInsertUser() {
		String sql = "insert into users(userid, userpw, username) values (?,?,?)";
		
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
	
	@Test
	public void testSelectUser() {
	    Connection con = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    String sql = "select * from users"; 

	    try {
	        con = data.getConnection();
	        stmt = con.createStatement(); 
	        rs = stmt.executeQuery(sql);  
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	if (rs != null) {
	            try { rs.close(); } catch (Exception e) {}
	        }
	        if (stmt != null) {
	            try { stmt.close(); } catch (Exception e) {}
	        }
	        if (con != null) {
	            try { con.close(); } catch (Exception e) { }
	        }
	    }
	}

}
