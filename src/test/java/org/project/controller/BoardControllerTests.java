package org.project.controller;

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
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@WebAppConfiguration
@Log4j
public class BoardControllerTests {
	//AlbumController�� ��� BoardController�� ������� �ۼ��Ͽ��� ������ ������ �׽�Ʈ�� �������� ����.
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	/* ����׽�Ʈ�� ���� �¾� */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	/* �Խñ� ���ȭ��(����¡) */
	@Test
	public void testListWithPaging() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list")
				.param("pageNum", "1")
				.param("amount", "10"))
				.andReturn()
				.getModelAndView()
				.getModelMap());		
	}
	
	/* �Խñ� ���ȭ�� */
	@Test
	public void testRegister() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
								.param("title", "test new title")
								.param("content", "test new content")
								.param("writer", "test new writer")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
		
	}
	
	/* �ۼ� �Խñ� Ȯ�� ȭ�� */
	@Test
	public void testGet() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/get").param("bno", "10"))
				.andReturn().getModelAndView().getModelMap());		
	}
	
	/* �ۼ� �Խñ� ���� ȭ�� */
	@Test
	public void testModifyPage() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/modify").param("bno", "10"))
				.andReturn().getModelAndView().getModelMap());		
	}
	
	/* �Խñ� ����ȭ�� */
	@Test
	public void testModify() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
								.param("bno", "2")
								.param("title", "2-title")
								.param("content", "2-content")
								.param("writer", "2-writer")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* �Խñ� ����ȭ�� */
	@Test
	public void testRemove() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
								.param("bno", "10")								
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
}
