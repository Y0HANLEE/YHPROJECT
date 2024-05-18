package org.project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
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
public class AdminControllerTests {	
	//ManagerController�� ��� AdminController�� ������� �ۼ��Ǿ� ������ �׽�Ʈ�� �������� ����.
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	/* ����׽�Ʈ�� ���� �¾� */
	@Before
	public void setup() {		        
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}		
	
	/* ȸ�����ȭ�� */
	@WithMockUser(username="admin1", roles={"ADMIN"})
	@Test
	public void testUserListPage() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/admin/list"))				
				.andReturn()
				.getModelAndView()
				.getModelMap());		
	}
		
	/* ����� ���� ����(�������) */	
	@WithMockUser(username="admin1", roles={"ADMIN"})
	@Test
	public void testUpdateUserAuth() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/admin/auth")
								.param("auth", "ROLE_USER")
								.param("userid", "qwerty")								
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}	
	
	/* ȸ��Ż�� */
	@WithMockUser(username="admin1", roles={"ADMIN"})
	@Test
	public void testDeleteUser() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/admin/delete")								
								.param("userid", "mgr1234")								
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* Ȩ ����ȭ�� */
	@WithMockUser(username="admin1", roles={"ADMIN"})
	@Test
	public void testUpdateHome() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/admin/home"))				
				.andReturn()
				.getModelAndView()
				.getModelMap());		
	}
	
	/* �Ұ� ����ȭ�� */
	@WithMockUser(username="admin1", roles={"ADMIN"})
	@Test
	public void testUpdateIntro() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/admin/intro"))				
				.andReturn()
				.getModelAndView()
				.getModelMap());		
	}
		
	/* Ȩȭ�� ����ó�� */
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
	
	/* �Ұ�ȭ�� ����ó�� */
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
