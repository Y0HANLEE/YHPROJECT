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
	//AlbumController의 경우 BoardController를 기반으로 작성하였기 때문에 별도의 테스트를 진행하지 않음.
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	/* 목업테스트를 위한 셋업 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	/* 게시글 목록화면(페이징) */
	@Test
	public void testListWithPaging() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list")
				.param("pageNum", "1")
				.param("amount", "10"))
				.andReturn()
				.getModelAndView()
				.getModelMap());		
	}
	
	/* 게시글 등록화면 */
	@Test
	public void testRegister() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
								.param("title", "test new title")
								.param("content", "test new content")
								.param("writer", "test new writer")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
		
	}
	
	/* 작성 게시글 확인 화면 */
	@Test
	public void testGet() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/get").param("bno", "10"))
				.andReturn().getModelAndView().getModelMap());		
	}
	
	/* 작성 게시글 수정 화면 */
	@Test
	public void testModifyPage() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/modify").param("bno", "10"))
				.andReturn().getModelAndView().getModelMap());		
	}
	
	/* 게시글 수정화면 */
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
	
	/* 게시글 삭제화면 */
	@Test
	public void testRemove() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
								.param("bno", "10")								
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
}
