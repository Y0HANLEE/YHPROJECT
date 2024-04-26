package org.project.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@WebAppConfiguration
@Log4j
public class MainControllerTests {	 
	// login, logout은 스프링시큐리티 기본제공이므로 테스트를 진행하지 않음.
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	/* 목업테스트를 위한 셋업 */
	@Before
	public void setup() {		        
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	/* 홈화면 */	
	@Test
	public void testHomepage() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/main/home"))				
				.andReturn()
				.getModelAndView()
				.getModelMap());		
	}
	
	/* 소개화면 */	
	@Test
	public void testIntropage() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/main/intro"))				
				.andReturn()
				.getModelAndView()
				.getModelMap());		
	}
	
	/* 아이디 찾기 */
	@Test
    public void testFindId() throws Exception {
        String name = "이요한";
        String email = "kevinyh@naver.com";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/main/findId")
                .param("name", name)
                .param("email", email))
                .andExpect(status().isOk());                
    }
    
	/* 비밀번호 초기화 */
    @Test
    public void testRenewalPw() throws Exception {        
        String userid = "kevinyh";
        String email = "kevinyh@naver.com";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/main/renewalPw")                
                .param("userid", userid)
                .param("email", email))		        
                .andExpect(status().isOk());
    }
		
}
