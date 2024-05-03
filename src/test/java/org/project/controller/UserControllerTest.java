package org.project.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.service.UserServiceImpl;
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
@ExtendWith(MockitoExtension.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@WebAppConfiguration
@Log4j
@SuppressWarnings("deprecation")
public class UserControllerTest {
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;
		
	@InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl uservice; 
    
    @Retention(RetentionPolicy.RUNTIME)
    @WithMockUser(username = "kevinyh", roles="USER")
    public @interface UserInfo { 
    	//@WithMockUser(username = "mgr1234", roles = "USER") ��� @WithUser�� ���
    }
	
	private MockMvc mockMvc;
	
	/* ����׽�Ʈ�� ���� �¾� */
	@Before
	public void setup() {		        
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        MockitoAnnotations.initMocks(this);
	}	
	
	/* ȸ������ ó�� ȭ�� */	
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
	
	/* ȸ������ ȭ�� */		
	@UserInfo
	@Test
	public void testProfile() throws Exception {
		mockMvc.perform(get("/user/profile")
				.param("userid", "kevinyh"))						
				.andReturn().getModelAndView().getViewName();
	}
	
	/* ȸ������ ȭ�� */
	@WithMockUser(value="mgr1234")	
	@Test
	public void testUpdatePage() throws Exception {
		mockMvc.perform(get("/user/update")
				.param("userid", "mgr1234"))						
				.andReturn().getModelAndView().getViewName();
	}
	
	/* ȸ������ ����ó�� */
	@WithMockUser(username="mgr1234", roles= {"USER"})	
	@Test
	public void testUpdatePost() throws Exception {		
		String resultPage = mockMvc.perform(post("/user/update")								
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
	
	/* ȸ�� Ż�� */
	@Test
	public void testDelete() throws Exception {		
		String resultPage = mockMvc.perform(post("/user/delete")								
							   .param("userid", "mgr1234")
							).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* ���̵� �ߺ� �˻� */
	@Test
	public void testCheckId() throws Exception {
	    mockMvc.perform(post("/user/checkId")	            
	            .param("userid", "kevinyh"))
	            .andExpect(status().is(200));
	}
			
	
	/* ��й�ȣ ���� - ������ ����� ���� �ʾ� nullpointer���� �߻� 
	@Test
	public void testUpdatePw() throws Exception {	
		UserVO user = new UserVO();
        user.setUserid("test00");
        user.setUserpw(pwEncoder.encode("test00"));
        
	    Principal principal = () -> "mgr1234";

	    // ��й�ȣ ���� ��û�� �����մϴ�.
	    mockMvc.perform(post("/user/updatePw")
	            .param("newPw", "asd12#$")
	            .param("userid", "mgr1234")
	            .param("oldPw", "qwe12#$")
	            .principal(principal) // Principal ��ü�� �����մϴ�.
	    ).andReturn().getModelAndView().getViewName();
	}
	*/	

	/* ����Ȯ�� : ��й�ȣ ���� �� uservice�� ������������ ���� �ʾ� null�� ���ϵǾ� ���� �߻�		
	@Test
    public void testCheckUser() throws Exception {
        UserVO user = new UserVO();
        user.setUserid("test00");
        user.setUserpw(pwEncoder.encode("test00")); 
       
        Principal principal = () -> "test00";

        String requestBody = "{\"userid\": \"test00\", \"userpw\": \"test00\"}";

        when(uservice.read("test00")).thenReturn(user);
        log.info("------------------------"+uservice.read("kevinyh"));

        mockMvc.perform(post("/user/checkUser")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());                
    }   */
}

