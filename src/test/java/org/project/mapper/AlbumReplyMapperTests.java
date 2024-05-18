package org.project.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Criteria;
import org.project.domain.Album.AlbumReplyVO;
import org.project.mapper.Album.AlbumReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class AlbumReplyMapperTests {
	@Setter(onMethod_ = @Autowired)
	private AlbumReplyMapper rmapper;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;
	
	@Test
	public void testMapper() {
		log.info(rmapper);
	}
	
	/* 댓글 테스트 더미 등록 */
	@Test
	public void testInsertContent() {
	    String sql = "insert into album_reply (ano, reply, replyer) values (?, ?, ?)";
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    try {       
	        con = ds.getConnection();
	        con.setAutoCommit(false);
	        pstmt = con.prepareStatement(sql);
	        
	        for (int k=1; k<=20; k++) {
	            for(int i=91; i <= 100; i++) {
	                pstmt.setInt(1, i);
	                pstmt.setString(2, "[reply] reply paging test" + k);
	                pstmt.setString(3, "admin");
	                pstmt.executeUpdate();
	            }
	        }	        
	        con.commit();
	    } catch (Exception e) {
	        try {
	            if (con != null) con.rollback();
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
	
	/* 댓글 등록 */
	@Test
	public void testInsert() {
		AlbumReplyVO reply = new AlbumReplyVO();		
		reply.setReply("test reply");
		reply.setReplyer("admin");
		
		log.info(rmapper.insert(reply));
	}
	
	/* 댓글 조회 */
	@Test
	public void testRead() {
		long rno = 201L;
		log.info(rmapper.read(rno));		
	}
		
	/* 댓글 수정*/
	@Test
	public void testUpdate() {
		AlbumReplyVO reply = rmapper.read(201L);		
		reply.setReply("up test");
		int result = rmapper.update(reply);
		if(result == 1) {
			log.info("-------------update success");
			log.info(reply);
		}
	}
	
	/* 댓글 삭제 */
	@Test
	public void testDelete() {
		long rno = 201L; 
		log.info("delete--------"+rmapper.delete(rno));
	}
	
	/* 특정 게시글에 달린 댓글목록 조회 */
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		long targetBno = 100L;
		List<AlbumReplyVO> replies = rmapper.getListWithPaging(cri, targetBno);
		replies.forEach(reply->log.info(reply));
	}
	
	/* 특정 게시글에 달린 댓글 개수 세기 */
	@Test
	public void testGetCount() {
		long targetBno = 100L;
		log.info("count replies--------"+rmapper.getCountByAno(targetBno));
	}
	
	/* 전체 댓글 목록 조회 */
	@Test
	public void testGetListAll() {
		int page = 1;
		int amount = 100;
		List<AlbumReplyVO> replies = rmapper.getListAll(new Criteria(page, amount));
		replies.forEach(reply -> log.info(reply));
	}
}
