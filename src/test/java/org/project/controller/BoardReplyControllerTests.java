package org.project.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Board.BoardReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class BoardReplyControllerTests {
	//AlbumReplyController의 경우 BoardReplyController를 기반으로 작성하였기 때문에 별도의 테스트를 진행하지 않음.
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	/* 목업테스트를 위한 셋업 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	/* 댓글 작성 */
	@WithMockUser(username="admin1", roles={"ADMIN"})
	@Test
	public void testAddReply() throws Exception{
		BoardReplyVO reply = new BoardReplyVO();
		reply.setBno(1L);
		reply.setReply("[REST] reply test");
		reply.setReplyer("qwerty");
		
		String jsonStr = new Gson().toJson(reply);
		log.info("[RestController TEST]-----------------------------jsonStr:"+jsonStr);
		
		mockMvc.perform(post("/board/replies/new")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonStr))
			.andExpect(status().is(200));
	}
	
	/* 댓글 조회 */
	@WithMockUser(username="admin1", roles={"ADMIN"})
	@Test
	public void testReadReply() throws Exception {
	    mockMvc.perform(get("/board/replies/{rno}", 25L))
	           .andExpect(status().isOk());
	}
	
	/* 댓글 목록 조회 */
	@Test
	public void testGetList() throws Exception{
		mockMvc.perform(get("/board/replies/pages/{bno}/{page}", 1L, 1))
        	   .andExpect(status().isOk());
	}
	
	/* 댓글 수정 */
	@Test
	public void testUpdateReply() throws Exception {
	    String requestBody = "{\"reply\": \"[REST] reply update test\"}";
	
	    mockMvc.perform(patch("/board/replies/{rno}", 25)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk());
	}
	
	/* 댓글 삭제 */
	@Test	
	public void testDeleteReply() throws Exception{
		String requestBody = "{\"replyer\": \"qwerty\"}";
		mockMvc.perform(delete("/board/replies/{rno}", 25)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)) //작성자 = 로그인사용자를 판단하기 위해 reply.replyer의 정보를 얻어오기 위해 파라미터로 @RequestBody BoardReplyVO타입의 reply요청
				.andExpect(status().isOk());
	}	
	
	/* get방식 예제	  
	@Test
	public void testGetRequest() throws Exception {
	    mockMvc.perform(get("/api/resource/{id}", 1))
	           .andExpect(status().isOk());
	}	  
	 
	/* post방식 예제
	@Test
	public void testPostRequest() throws Exception {
	    String requestBody = "{\"key\": \"value\"}";

	    mockMvc.perform(post("/api/resource")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isCreated());
	}
	
	/* put/patch방식 예제 (perform 방식을 수정해서 쓰면된다.)
	@Test
	public void testPatchRequest() throws Exception {
	    String requestBody = "{\"key\": \"updatedValue\"}";
	
	    mockMvc.perform(patch("/api/resource/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk());
	} 
	
	/* delete방식 예제
	@Test
	public void testDeleteRequest() throws Exception {
	    mockMvc.perform(delete("/api/resource/{id}", 1))
	           .andExpect(status().isNoContent());
	}
	 */

}
