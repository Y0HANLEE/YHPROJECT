package org.project.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Criteria;
import org.project.domain.Board.BoardReplyVO;
import org.project.mapper.Board.BoardReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardReplyMapperTests {

	@Setter(onMethod_ = @Autowired)
	private BoardReplyMapper rmapper;
	
	@Test
	public void testMapper() {
		log.info(rmapper);
	}
	
	/* ��� ��� */
	@Test
	public void testInsert() {
		BoardReplyVO reply = new BoardReplyVO();
		reply.setBno(32L);
		reply.setReply("test reply");
		reply.setReplyer("abc");
		
		log.info(rmapper.insert(reply));
	}
	
	/* ��� ��ȸ */
	@Test
	public void testRead() {
		log.info(rmapper.read(2L));		
	}
	
	/* ��� ����*/
	@Test
	public void testUpdate() {
		BoardReplyVO reply = rmapper.read(1L);		
		reply.setReply("test reply11");
		
		log.info("update--------"+rmapper.update(reply));
	}
	
	/* ��� ���� */
	@Test
	public void testDelete() {
		log.info("delete--------"+rmapper.delete(1L));
	}
	
	/* Ư�� �Խñۿ� �޸� ��۸�� ��ȸ */
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		
		List<BoardReplyVO> reple = rmapper.getListWithPaging(cri, 32L);
		reple.forEach(reply->log.info(reply));
	}
	
	/* Ư�� �Խñۿ� �޸� ��۸�� ��ȸ */
	@Test
	public void testListPaging() {
		Criteria cri = new Criteria(1,10);
		
		List<BoardReplyVO> reple = rmapper.getListWithPaging(cri, 32L);
		reple.forEach(reply->log.info(reply));
	}
	
	/* Ư�� �Խñۿ� �޸� ��� ���� ���� */
	@Test
	public void testGetCount() {
		log.info("count replies--------"+rmapper.getCountByBno(32L));
	}
}
