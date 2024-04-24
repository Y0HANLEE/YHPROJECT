package org.project.controller;


import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class SecurityControllerTests {
	
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	/* 목업테스트를 위한 셋업 */
	@Before
	public void setup() {		        
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
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

	@Test
    public void testFindId() throws Exception {
        String name = "John";
        String email = "john@example.com";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/findId")
                .param("name", name)
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) // 응답이 배열인지 확인
                .andExpect(jsonPath("$[0]").value("user1")) // 예상되는 아이디가 반환되는지 확인
                .andExpect(jsonPath("$[1]").value("user2")); // 추가적인 아이디가 반환되는지 확인
    }
    
    @Test
    public void testRenewalPw() throws Exception {
        String randomPw = "randomPassword";
        String userid = "user1";
        String email = "john@example.com";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/renewalPw")
                .param("randomPw", randomPw)
                .param("userid", userid)
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("success")); // 응답이 "success"인지 확인
    }
}
