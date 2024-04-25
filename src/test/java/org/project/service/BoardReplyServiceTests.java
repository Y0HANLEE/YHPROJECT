package org.project.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Criteria;
import org.project.domain.Board.BoardReplyVO;
import org.project.service.Board.BoardReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardReplyServiceTests {
	//AlbumReplyService는 BoardReplyService를 기반으로 작성되어 별도의 테스트는 진행하지 않음.
	@Setter(onMethod_ = @Autowired)
	private BoardReplyService rservice;
	
	/* ReplyService 객체 주입가능여부 확인 */
	@Test
	public void testExist() {
		log.info(rservice);
		assertNotNull(rservice);
	}
	
	/* 게시글 등록 */
	@Test
	public void testRegister() {
		BoardReplyVO reply = new BoardReplyVO();
		reply.setBno(32L);
		reply.setReply("test reply");
		reply.setReplyer("test replyer");
		
		rservice.register(reply);
		log.info("result------------------------------------"+reply);
	}
	
	/* 게시글 조회 */
	@Test
	public void testRead() {
		log.info("read---------"+rservice.get(4L));		
	}
	
	/* 게시글 수정 */
	@Test
	public void testModify() {
		BoardReplyVO reply = rservice.get(4L);
		
		// 경우1 수정없음 : 현상유지
		if(reply == null) {
			return;
		}
		// 경우2 수정있음		
		reply.setReply("test reply");
		reply.setReplyer("test replyer");
		
		log.info("MODIFY Result : " + rservice.modify(reply));
	}
	
	/* 게시글 삭제 */
	@Test
	public void testRemove() {
		log.info("REMOVE Result : "+rservice.remove(4L));
	}
	
	/* 게시글 목록 조회(페이징) */ 	
	@Test
	public void testGetList() {
		log.info(rservice.getListPage(new Criteria(1,10), 203L));
	}
	
	/* 게시글 전체 목록 조회 */ 	
	@Test
	public void testGetListAll() {
		rservice.getListAll(new Criteria(1,5));
	}
}

