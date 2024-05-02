package org.project.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Criteria;
import org.project.domain.Album.AlbumVO;
import org.project.mapper.Album.AlbumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class AlbumMapperTests {
	//AlbumMapper의 경우 AlbumMapper를 기반으로 작성했기 때문에 별도의 테스트를 진행하지 않음. > search의 경우에만 keyword가 추가되어 테스트 진행 
	@Setter(onMethod_ = @Autowired)
	private AlbumMapper amapper;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;
		
	/* 게시판 목록 조회(페이징) */
	@Test
	public void testGetListWithPaging() {
		Criteria cri = new Criteria();
		cri.setPageNum(1);
		cri.setAmount(10);		
		amapper.getListWithPaging(cri).forEach(album->log.info(album));
	}
		
	/*게시글 등록(insertSelectKey)*/
	@Test
	public void testInsertSelectKey() {
		AlbumVO album = new AlbumVO();
		album.setTitle("mysqlTest");
		album.setContent("mysqlTest");
		album.setWriter("admin");
		
		amapper.insertSelectKey(album);
		log.info(album);
	}
	
	/* 게시글 테스트 더미 등록 */
	@Test
	public void testInsertContent() {
	    String sql = "insert into album(title, content, writer) values (?,?,?)";
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    try {       
	        con = ds.getConnection();
	        con.setAutoCommit(false); // 수동 커밋 설정
	        pstmt = con.prepareStatement(sql);

	        for(int i=1; i<=100; i++) {          
	            pstmt.setString(1, "[title] paging test"+i);
	            pstmt.setString(2, "[content] paging test"+i);
	            pstmt.setString(3, "admin");
	            pstmt.executeUpdate(); // 쿼리 실행
	        }
	        con.commit(); // 커밋 수행
	    } catch (Exception e) {
	        try {
	            if (con != null) {
	                con.rollback(); // 롤백 수행
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
	
	/* 게시글 조회 + 조회수 증가 */
	@Test
	public void testRead() {
		AlbumVO album = amapper.read(101L);
		amapper.upHit(101L);
		log.info(album);
	}
		
	/* 게시글 수정*/
	@Test
	public void testUpdate() {
		AlbumVO album = new AlbumVO();
		album.setAno(100L);
		album.setTitle("전주여행");
		album.setContent("전주여행");
		album.setWriter("admin");
		album.setLocation("전주 한옥마을");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {        	
    		album.setStartDate(sdf.parse("2022-05-03"));
    		album.setEndDate(sdf.parse("2022-05-06"));         
        } catch (ParseException e) {          
        	e.printStackTrace();
        }  
					
		if (amapper.update(album) == 1) {
			log.info("Update Success");
			log.info(album);
		} else {
			log.info("Update fail");			
		}		
	}

	/* 게시글 삭제 */
	@Test
	public void testDelete() {
		long ano = 101L;		
		if (amapper.delete(ano) == 1) {
			log.info("Delete Success");			 
		} else {
			log.info("Delete fail");			
		}		
	}
	
	/* 총 게시물 수 계산 */
	@Test
	public void testGetTotal() {
		Criteria cri = new Criteria();
		log.info("-----------------"+amapper.getTotalCnt(cri));
	}
		
		
	/* 사진게시판 검색 기능 */
	@Test
	public void testSearchAlbum() {
		Criteria cri = new Criteria();
		cri.setKeyword("2022-05-04");
		cri.setType("D");
		
		amapper.getListWithPaging(cri).forEach(album->log.info(album));;		
	}
}
