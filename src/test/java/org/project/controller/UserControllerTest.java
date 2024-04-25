package org.project.controller;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
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
public class UserControllerTest {
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	/* ����׽�Ʈ�� ���� �¾� */
	@Before
	public void setup() {		        
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();		
	}	

	/* ȸ������ ȭ�� */	
	@WithAnonymousUser
	@Test
	public void testJoin() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/join")								
								.param("userid", "mgr1234")								
								.param("userpw", "qwe12#$")								
								.param("name", "�̿���")
								.param("gender", "M")
								.param("email", "kevinyh@naver.com")
								.param("phone", "010-7133-2105")
								.param("birth", "1990-07-03")
								.param("zonecode", "14258")
								.param("address", "���������� �߱� ����� 257")
								.param("addressDetail", "1��")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* ȸ������ ���� */
	@WithMockUser(username="mgr1234", roles= {"USER"})	
	@Test
	public void testUpdate() throws Exception {		
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/update")								
								.param("userid", "mgr1234")								
								//.param("userpw", "qwe12#$")								
								//.param("name", "�̿���")
								//.param("gender", "M")
								.param("email", "kevinyh@naver.com")
								.param("phone", "010-0000-0000")
								//.param("birth", "1990-07-03")
								.param("zonecode", "14258")
								.param("address", "���������� �߱� ����� 257")
								.param("addressDetail", "1��")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* ���� �� �Խñ� ��ȸ ȭ�� */	
	@WithMockUser(value="qwerty")
	@Test
	public void testMyContents() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.get("/user/contents")								
								.param("boardType", "1").param("userid", "qwerty") // 1:�ϹݰԽ���, 2:�����Խ���
								.param("amount", "100000").param("pageNum", "1")
								.param("type", "").param("keyword", "")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* ���� �� ��� ��ȸ ȭ�� */	
	@WithMockUser(value="qwerty")
	@Test
	public void testMyComments() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.get("/user/comments")								
								.param("boardType", "2.1").param("userid", "qwerty") // 1.1:�ϹݰԽ��Ǵ��, 2.1:�����Խ��Ǵ��
								.param("amount", "100000").param("pageNum", "1")
								.param("type", "").param("keyword", "")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* ��й�ȣ ���� - �ϴ� ���� ����, mockMVC �׽�Ʈ�� ���� �� �𸣱� ������ �����ϱ⿣ �ʹ� �����ɸ� */
	@Test
	public void testUpdatePw() throws Exception {		
	    // Principal ��ü�� �����Ͽ� ����� ������ �����մϴ�.
	    Principal principal = () -> "mgr1234";

	    // ��й�ȣ ���� ��û�� �����մϴ�.
	    String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/updatePw")
	            .param("newPw", "asd12#$")
	            .param("userid", "mgr1234")
	            .param("oldPw", "qwe12#$")
	            .principal(principal) // Principal ��ü�� �����մϴ�.
	    ).andReturn().getModelAndView().getViewName();

	    // ��� �������� ����մϴ�.
	    log.info(resultPage);
	}
	
	@Test
	public void testDelete() throws Exception {		
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/delete")								
							   .param("userid", "mgr1234")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
}

