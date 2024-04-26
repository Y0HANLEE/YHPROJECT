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
	//AlbumReplyController�� ��� BoardReplyController�� ������� �ۼ��Ͽ��� ������ ������ �׽�Ʈ�� �������� ����.
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	/* ����׽�Ʈ�� ���� �¾� */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	/* ��� �ۼ� */
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
	
	/* ��� ��ȸ */
	@WithMockUser(username="admin1", roles={"ADMIN"})
	@Test
	public void testReadReply() throws Exception {
	    mockMvc.perform(get("/board/replies/{rno}", 25L))
	           .andExpect(status().isOk());
	}
	
	/* ��� ��� ��ȸ */
	@Test
	public void testGetList() throws Exception{
		mockMvc.perform(get("/board/replies/pages/{bno}/{page}", 1L, 1))
        	   .andExpect(status().isOk());
	}
	
	/* ��� ���� */
	@Test
	public void testUpdateReply() throws Exception {
	    String requestBody = "{\"reply\": \"[REST] reply update test\"}";
	
	    mockMvc.perform(patch("/board/replies/{rno}", 25)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk());
	}
	
	/* ��� ���� */
	@Test	
	public void testDeleteReply() throws Exception{
		String requestBody = "{\"replyer\": \"qwerty\"}";
		mockMvc.perform(delete("/board/replies/{rno}", 25)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)) //�ۼ��� = �α��λ���ڸ� �Ǵ��ϱ� ���� reply.replyer�� ������ ������ ���� �Ķ���ͷ� @RequestBody BoardReplyVOŸ���� reply��û
				.andExpect(status().isOk());
	}	
	
	/* get��� ����	  
	@Test
	public void testGetRequest() throws Exception {
	    mockMvc.perform(get("/api/resource/{id}", 1))
	           .andExpect(status().isOk());
	}	  
	 
	/* post��� ����
	@Test
	public void testPostRequest() throws Exception {
	    String requestBody = "{\"key\": \"value\"}";

	    mockMvc.perform(post("/api/resource")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isCreated());
	}
	
	/* put/patch��� ���� (perform ����� �����ؼ� ����ȴ�.)
	@Test
	public void testPatchRequest() throws Exception {
	    String requestBody = "{\"key\": \"updatedValue\"}";
	
	    mockMvc.perform(patch("/api/resource/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk());
	} 
	
	/* delete��� ����
	@Test
	public void testDeleteRequest() throws Exception {
	    mockMvc.perform(delete("/api/resource/{id}", 1))
	           .andExpect(status().isNoContent());
	}
	 */

}
