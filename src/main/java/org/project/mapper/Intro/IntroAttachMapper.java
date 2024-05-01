package org.project.mapper.Intro;

import java.util.List;

import org.project.domain.IntroAttachVO;

public interface IntroAttachMapper {
	//등록
	public void insert(IntroAttachVO attach); // 새 첨부파일 등록
	//조회
	public List<IntroAttachVO> findByBoard(int boardtype); //게시판에 달린 첨부파일목록조회 
	public List<IntroAttachVO> getOldFiles(); // 하루전 첨부파일목록 조회 | 비교 후 삭제를 위함
	//수정 : 모두 삭제 후 재등록으로 처리
	//삭제
	public void deleteAll(int boardtype);	
}
