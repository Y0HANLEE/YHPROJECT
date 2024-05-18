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
	//AlbumMapper�� ��� AlbumMapper�� ������� �ۼ��߱� ������ ������ �׽�Ʈ�� �������� ����. > search�� ��쿡�� keyword�� �߰��Ǿ� �׽�Ʈ ���� 
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
		amapper.getListWithPaging(cri).forEach(album->log.info(album));
	}
		
	/*�Խñ� ���(insertSelectKey)*/
	@Test
	public void testInsertSelectKey() {
		AlbumVO album = new AlbumVO();
		album.setTitle("mysqlTest");
		album.setContent("mysqlTest");
		album.setWriter("admin");
		
		amapper.insertSelectKey(album);
		log.info(album);
	}
	
	/* �Խñ� �׽�Ʈ ���� ��� */
	@Test
	public void testInsertContent() {
	    String sql = "insert into album(title, content, writer) values (?,?,?)";
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
		AlbumVO album = amapper.read(101L);
		amapper.upHit(101L);
		log.info(album);
	}
		
	/* �Խñ� ����*/
	@Test
	public void testUpdate() {
		AlbumVO album = new AlbumVO();
		album.setAno(100L);
		album.setTitle("���ֿ���");
		album.setContent("���ֿ���");
		album.setWriter("admin");
		album.setLocation("���� �ѿ�����");
		
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

	/* �Խñ� ���� */
	@Test
	public void testDelete() {
		long ano = 101L;		
		if (amapper.delete(ano) == 1) {
			log.info("Delete Success");			 
		} else {
			log.info("Delete fail");			
		}		
	}
	
	/* �� �Խù� �� ��� */
	@Test
	public void testGetTotal() {
		Criteria cri = new Criteria();
		log.info("-----------------"+amapper.getTotalCnt(cri));
	}
		
		
	/* �����Խ��� �˻� ��� */
	@Test
	public void testSearchAlbum() {
		Criteria cri = new Criteria();
		cri.setKeyword("2022-05-04");
		cri.setType("D");
		
		amapper.getListWithPaging(cri).forEach(album->log.info(album));;		
	}
}
