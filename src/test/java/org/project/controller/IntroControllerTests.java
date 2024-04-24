package org.project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.security.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@WebAppConfiguration
@Log4j
public class IntroControllerTests {
	
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
		
	/* Ȩȭ�� ���� */
	@WithMockUser(username="admin1", roles={"ADMIN"})
	@Test
	public void testUpdateHomepage() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/admin/home")
								.param("title_title", "�׽�Ʈ Ȩȭ���Դϴ�.")
								.param("title_intro", "")
								.param("map_address", "")
								.param("map_addressdetail", "")
								.param("map_caption", "") 
								.param("map_intro", "") 
								.param("map_title", "")
								.param("intro", "update home_IntroController Test")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* �Ұ�ȭ�� ���� */
	@WithMockUser(username="admin1", roles={"ADMIN"})
	@Test
	public void testUpdateIntropage() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/admin/intro")	
								.param("title_title", "�׽�Ʈ ��Ʈ��ȭ���Դϴ�.")
								.param("title_intro", "")
								.param("map_address", "")
								.param("map_addressdetail", "")
								.param("map_caption", "") 
								.param("map_intro", "") 
								.param("map_title", "")
								.param("intro", "update intro_IntroController Test")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}	
}
