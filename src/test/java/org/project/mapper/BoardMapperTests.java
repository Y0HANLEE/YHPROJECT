package org.project.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Criteria;
import org.project.domain.Board.BoardVO;
import org.project.mapper.Board.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mchange.v2.cfg.PropertiesConfigSource.Parse;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {

	@Setter(onMethod_ = @Autowired)
	private BoardMapper bmapper;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;
		
	/* �Խ��� ��� ��ȸ(����¡) */
	@Test
	public void testGetListWithPaging() {
		Criteria cri = new Criteria();
		cri.setPageNum(1);
		cri.setAmount(20);
		bmapper.getListWithPaging(cri).forEach(board->log.info(board));
	}
		
	/*�Խñ� ���(insertSelectKey)*/
	@Test
	public void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("t-test6");
		board.setContent("c-test6");
		board.setWriter("w-test6");
		
		bmapper.insertSelectKey(board);
		log.info(board);
	}
	
	/* �Խñ� �׽�Ʈ ���� ��� */
	@Test
	public void testInsertContent() {
		String sql = "insert into board(title, content, writer) values (?,?,?)";
		for(int i=0; i<100; i++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {				
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
								
				pstmt.setString(1, "[title] paging test"+i);
				pstmt.setString(2, "[content] paging test"+i);
				pstmt.setString(3, "[writer] tester"+i);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) {try {pstmt.close();} catch (Exception e) {} }
				if(con != null) {try {con.close();} catch (Exception e) {} }
			}
		}
				
	}
	
	/* �Խñ� ��ȸ + ��ȸ�� ���� */
	@Test
	public void testRead() {
		BoardVO board = bmapper.read(8L);
		bmapper.upHit(8L);
		log.info(board);
	}
	
	/* �Խñ� ���� */
	@Test
	public void testDelete() {
		String del = Integer.toString(bmapper.delete(6L));
		if (del.equals("1")) {
			log.info("Delete Success");			 
		} else {
			log.info("Delete fail");			
		}		
	}
	
	/* �Խñ� ����*/
	@Test
	public void testUpdate() {
		BoardVO board = new BoardVO();
		board.setBno(8L);
		board.setTitle("f-title");
		board.setContent("f-content");
		board.setWriter("f-writer");
		
		String update = Integer.toString(bmapper.update(board));
		if (update.equals("1")) {
			log.info("Update Success");			 
		} else {
			log.info("Update fail");			
		}		
	}
	
	/* �� �Խù� �� ��� */
	@Test
	public void testGetTotal() {
		log.info(bmapper.getTotalCount(new Criteria()));
	}
		
	/* �˻� ��� */
	@Test
	public void testSearch() {
		Criteria cri = new Criteria();
		cri.setKeyword("te");
		cri.setType("T");
		
		bmapper.getListWithPaging(cri).forEach(board->log.info(board));;		
	}	
}
