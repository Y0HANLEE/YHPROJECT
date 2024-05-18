package org.project.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.Board.BoardAttachVO;
import org.project.mapper.Board.BoardAttachMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardAttachMapperTests {
	//AlbumAttachMapper, IntroAttachMapper의 경우, BoardAttachMapper를 기반으로 작성하여 구조가 같으므로 별도의 테스트를 진행하지 않음.
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper bamapper;
		
	@Test
	public void testInsertAttach() {
		BoardAttachVO attach = new BoardAttachVO();
		//테스트를 위해 임시로 작성
		attach.setBno(1L);
		attach.setFileName("test2.jpg");
		attach.setFileType(true);
		attach.setUploadPath("C:\\upload");
		attach.setUuid("gengi;opehjiejafgqgoh");
		bamapper.insert(attach);
	}
	
	@Test
	public void testFindAttach() {
		bamapper.findByBno(1L);
	}
	
	@Test
	public void testGetOldFiles() {
		List<BoardAttachVO> attachs = bamapper.getOldFiles(); //하루전 파일만 검색 
		attachs.forEach(attach->log.info(attach));		
	}
	
	@Test
	public void testDeleteAllAttach() {
		bamapper.deleteAll(1L);
	}
}
