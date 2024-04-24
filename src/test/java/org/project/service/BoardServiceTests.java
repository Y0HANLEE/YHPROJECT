package org.project.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Criteria;
import org.project.domain.Board.BoardVO;
import org.project.service.Board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

	@Setter(onMethod_ = @Autowired)
	private BoardService bservice;
	
	/* BoardService ��ü ���԰��ɿ��� Ȯ�� */
	@Test
	public void testExist() {
		log.info(bservice);
		assertNotNull(bservice);
	}
	
	/* �Խñ� ��� */
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("j-title");
		board.setContent("j-content");
		board.setWriter("j-writer");
		
		bservice.register(board);
		log.info("result------------------------------------"+board);
	}
	
	/* �Խñ� ��ȸ + ��ȸ�� ���� */
	@Test
	public void testRead() {
		bservice.upHit(8L);
		log.info("read---------"+bservice.get(8L));		
	}
	
	/* �Խñ� ���� */
	@Test
	public void testModify() {
		BoardVO board = bservice.get(1L);
		
		// ���1 �������� : ��������
		if(board == null) {
			return;
		}
		// ���2 ��������
		board.setTitle("1-title");
		board.setContent("1-content");
		board.setWriter("1-writer");
		
		log.info("MODIFY Result : " + bservice.modify(board));
	}
	
	/* �Խñ� ���� */
	@Test
	public void testRemove() {
		log.info("REMOVE Result : "+bservice.remove(14L));
	}
	
	/* �Խñ� ��� ��ȸ(�⺻) 	
	@Test
	public void testPrintList() {
		bservice.getList().forEach(board -> log.info(board));	
	}
	*/
	
	/* �Խñ� ��� ��ȸ(����¡) */ 	
	@Test
	public void testPrintList() {		
		bservice.getList(new Criteria(1,50)).forEach(board -> log.info(board));	
	}
	
	/* �� �Խù� �� ��� */
	@Test
	public void testTotalCount() {
		log.info("Total : "+bservice.getTotal(new Criteria()));
	}
}

