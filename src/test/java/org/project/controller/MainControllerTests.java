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
	// login, logout�� ��������ť��Ƽ �⺻�����̹Ƿ� �׽�Ʈ�� �������� ����.
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	/* ����׽�Ʈ�� ���� �¾� */
	@Before
	public void setup() {		        
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	/* Ȩȭ�� */	
	@Test
	public void testHomepage() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/main/home"))				
				.andReturn()
				.getModelAndView()
				.getModelMap());		
	}
	
	/* �Ұ�ȭ�� */	
	@Test
	public void testIntropage() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/main/intro"))				
				.andReturn()
				.getModelAndView()
				.getModelMap());		
	}
	
	/* ���̵� ã�� */
	@Test
    public void testFindId() throws Exception {
        String name = "�̿���";
        String email = "kevinyh@naver.com";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/main/findId")
                .param("name", name)
                .param("email", email))
                .andExpect(status().isOk());                
    }
    
	/* ��й�ȣ �ʱ�ȭ */
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
