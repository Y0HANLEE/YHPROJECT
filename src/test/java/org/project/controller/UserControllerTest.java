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
	
	/* 목업테스트를 위한 셋업 */
	@Before
	public void setup() {		        
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();		
	}	

	/* 회원가입 화면 */	
	@WithAnonymousUser
	@Test
	public void testJoin() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/join")								
								.param("userid", "mgr1234")								
								.param("userpw", "qwe12#$")								
								.param("name", "이요한")
								.param("gender", "M")
								.param("email", "kevinyh@naver.com")
								.param("phone", "010-7133-2105")
								.param("birth", "1990-07-03")
								.param("zonecode", "14258")
								.param("address", "대전광역시 중구 현백로 257")
								.param("addressDetail", "1층")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* 회원정보 수정 */
	@WithMockUser(username="mgr1234", roles= {"USER"})	
	@Test
	public void testUpdate() throws Exception {		
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/update")								
								.param("userid", "mgr1234")								
								//.param("userpw", "qwe12#$")								
								//.param("name", "이요한")
								//.param("gender", "M")
								.param("email", "kevinyh@naver.com")
								.param("phone", "010-0000-0000")
								//.param("birth", "1990-07-03")
								.param("zonecode", "14258")
								.param("address", "대전광역시 중구 현백로 257")
								.param("addressDetail", "1층")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* 내가 쓴 게시글 조회 화면 */	
	@WithMockUser(value="qwerty")
	@Test
	public void testMyContents() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.get("/user/contents")								
								.param("boardType", "1").param("userid", "qwerty") // 1:일반게시판, 2:사진게시판
								.param("amount", "100000").param("pageNum", "1")
								.param("type", "").param("keyword", "")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* 내가 쓴 댓글 조회 화면 */	
	@WithMockUser(value="qwerty")
	@Test
	public void testMyComments() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.get("/user/comments")								
								.param("boardType", "2.1").param("userid", "qwerty") // 1.1:일반게시판댓글, 2.1:사진게시판댓글
								.param("amount", "100000").param("pageNum", "1")
								.param("type", "").param("keyword", "")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* 비밀번호 수정 - 일단 개발 진행, mockMVC 테스트에 대해 잘 모르기 떄문에 진행하기엔 너무 오래걸림 */
	@Test
	public void testUpdatePw() throws Exception {		
	    // Principal 객체를 생성하여 사용자 정보를 설정합니다.
	    Principal principal = () -> "mgr1234";

	    // 비밀번호 변경 요청을 수행합니다.
	    String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/updatePw")
	            .param("newPw", "asd12#$")
	            .param("userid", "mgr1234")
	            .param("oldPw", "qwe12#$")
	            .principal(principal) // Principal 객체를 설정합니다.
	    ).andReturn().getModelAndView().getViewName();

	    // 결과 페이지를 출력합니다.
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

