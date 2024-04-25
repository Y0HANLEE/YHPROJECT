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
	//AlbumReplyService�� BoardReplyService�� ������� �ۼ��Ǿ� ������ �׽�Ʈ�� �������� ����.
	@Setter(onMethod_ = @Autowired)
	private BoardReplyService rservice;
	
	/* ReplyService ��ü ���԰��ɿ��� Ȯ�� */
	@Test
	public void testExist() {
		log.info(rservice);
		assertNotNull(rservice);
	}
	
	/* �Խñ� ��� */
	@Test
	public void testRegister() {
		BoardReplyVO reply = new BoardReplyVO();
		reply.setBno(32L);
		reply.setReply("test reply");
		reply.setReplyer("test replyer");
		
		rservice.register(reply);
		log.info("result------------------------------------"+reply);
	}
	
	/* �Խñ� ��ȸ */
	@Test
	public void testRead() {
		log.info("read---------"+rservice.get(4L));		
	}
	
	/* �Խñ� ���� */
	@Test
	public void testModify() {
		BoardReplyVO reply = rservice.get(4L);
		
		// ���1 �������� : ��������
		if(reply == null) {
			return;
		}
		// ���2 ��������		
		reply.setReply("test reply");
		reply.setReplyer("test replyer");
		
		log.info("MODIFY Result : " + rservice.modify(reply));
	}
	
	/* �Խñ� ���� */
	@Test
	public void testRemove() {
		log.info("REMOVE Result : "+rservice.remove(4L));
	}
	
	/* �Խñ� ��� ��ȸ(����¡) */ 	
	@Test
	public void testGetList() {
		log.info(rservice.getListPage(new Criteria(1,10), 203L));
	}
	
	/* �Խñ� ��ü ��� ��ȸ */ 	
	@Test
	public void testGetListAll() {
		rservice.getListAll(new Criteria(1,5));
	}
}

