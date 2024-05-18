package org.project.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Criteria;
import org.project.domain.Board.BoardVO;
import org.project.mapper.Album.AlbumMapper;
import org.project.mapper.Board.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	//AlbumMapper�� ��� BoardMapper�� ������� �ۼ��߱� ������ ������ �׽�Ʈ�� �������� ����. > search�� ��쿡�� keyword�� �߰��Ǿ� �׽�Ʈ ���� 
	@Setter(onMethod_ = @Autowired)
	private BoardMapper bmapper;
	
	@Setter(onMethod_ = @Autowired)
	private AlbumMapper amapper;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;
		
	/* �Խ��� ��� ��ȸ(����¡) */
	@Test
	public void testGetListWithPaging() {
		Criteria cri = new Criteria();
		cri.setPageNum(1);
		cri.setAmount(10);		
		bmapper.getListWithPaging(cri).forEach(board->log.info(board));
	}
		
	/*�Խñ� ���(insertSelectKey)*/
	@Test
	public void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("mysqlTest2");
		board.setContent("mysqlTest2");
		board.setWriter("admin");
		
		bmapper.insertSelectKey(board);
		log.info(board);
	}
	
	/* �Խñ� �׽�Ʈ ���� ��� */
	@Test
	public void testInsertContent() {
	    String sql = "insert into board(title, content, writer) values (?,?,?)";
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    try {       
	        con = ds.getConnection();
	        con.setAutoCommit(false); // ���� Ŀ�� ����
	        pstmt = con.prepareStatement(sql);

	        for(int i=1; i<=100; i++) {          
	            pstmt.setString(1, "[title] paging test"+i);
	            pstmt.setString(2, "[content] paging test"+i);
	            pstmt.setString(3, "admin");
	            pstmt.executeUpdate(); // ���� ����
	        }
	        con.commit(); // Ŀ�� ����
	    } catch (Exception e) {
	        try {
	            if (con != null) {
	                con.rollback(); // �ѹ� ����
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	/* �Խñ� ��ȸ + ��ȸ�� ���� */
	@Test
	public void testRead() {
		BoardVO board = bmapper.read(105L);
		bmapper.upHit(105L);
		log.info(board);
	}
	
	/* �Խñ� ���� */
	@Test
	public void testDelete() {
		String del = Integer.toString(bmapper.delete(3L));
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
		board.setBno(104L);
		board.setTitle("up-title");
		board.setContent("up-content");
		board.setWriter("admin");
		
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
		Criteria cri = new Criteria();		
		log.info("-----------------"+bmapper.getTotalCount(cri));
	}
		
	/* �˻� ��� */
	@Test
	public void testSearchBoard() {
		Criteria cri = new Criteria();
		cri.setKeyword("83");
		cri.setType("T");
		
		bmapper.getListWithPaging(cri).forEach(board->log.info(board));;		
	}
	
	/* �����Խ��� �˻� ��� */
	@Test
	public void testSearchAlbum() {
		Criteria cri = new Criteria();
		cri.setKeyword("��Ű����");
		cri.setType("L");
		
		amapper.getListWithPaging(cri).forEach(album->log.info(album));;		
	}
}
