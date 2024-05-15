package org.project.service.Album;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.Album.AlbumAttachVO;
import org.project.domain.Album.AlbumVO;
import org.project.mapper.Album.AlbumAttachMapper;
import org.project.mapper.Album.AlbumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class AlbumServiceImpl implements AlbumService{
	
	@Setter(onMethod_ = @Autowired)
	private AlbumMapper almapper;
	
	@Setter(onMethod_ = @Autowired)
	private AlbumAttachMapper amapper;

	/* 등록 + 첨부파일 등록 */
	@Transactional
	@Override
	public void register(AlbumVO album) {
		almapper.insertSelectKey(album);
		//첨부파일x
		if(album.getAttachList() == null || album.getAttachList().size() <= 0) {		
			return;
		} 				
		//첨부파일o
		album.getAttachList().forEach(attach -> {
			attach.setAno(album.getAno());
			amapper.insert(attach);
		});	
	}
	
	/* 조회 + 조회수 증가 */
	@Override	
	public AlbumVO read(Long ano) {
		upHit(ano); //게시글 조회시 조회수 1증가
		return almapper.read(ano);
	}
	
	/* 목록 조회(페이징+검색조건) */
	@Override
	public List<AlbumVO> getList(Criteria cri){
		log.info("-----------------------"+almapper.getListWithPaging(cri));
		return almapper.getListWithPaging(cri);	
	}
	
	/* 게시물 첨부파일 목록 조회 */
	@Override
	public List<AlbumAttachVO> attachList(Long ano){
		return amapper.findByAno(ano);
	}
	
	/* 게시물 수 계산 */
	@Override
	public int totalCnt(Criteria cri) {
		return almapper.getTotalCnt(cri);
	}
	
	/* 수정 + 첨부파일 수정 */
	@Transactional
	@Override	
	public int modify(AlbumVO album) {
		//첨부파일이 있을 경우, 일단 모두 삭제 후 추가 처리
		int result = almapper.update(album);		
		
		amapper.deleteAll(album.getAno()); //삭제
		log.info("[albumService]--------------------------delete");
		
		if(result==1 && album.getAttachList()!=null && album.getAttachList().size()>0) {
			album.getAttachList().forEach(attach -> {
				attach.setAno(album.getAno());
				amapper.insert(attach); //재등록
				log.info("[albumService]--------------------------"+attach);
			});
		}
		
		return result;
	}
	
	/* 조회수 증가 */
	public void upHit(Long ano) {
		almapper.upHit(ano);
	}	
	
	/* 삭제 + 첨부파일 삭제 */
	@Override
	public int remove(Long ano) {
		amapper.deleteAll(ano); // 첨부파일 삭제
		return almapper.delete(ano); // 게시글 삭제
	}
	
}
