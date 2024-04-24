package org.project.service.Album;

import org.project.domain.Criteria;
import org.project.domain.Album.AlbumReplyPageDTO;
import org.project.domain.Album.AlbumReplyVO;
import org.project.mapper.Album.AlbumMapper;
import org.project.mapper.Album.AlbumReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
public class AlbumReplyServiceImpl implements AlbumReplyService{
	
	@Setter(onMethod_ = @Autowired)
	private AlbumReplyMapper rmapper;
			
	@Setter(onMethod_ = @Autowired)
	private AlbumMapper amapper;
	
	/* 댓글 등록 + 댓글수+1 */
	@Transactional
	@Override
	public int register(AlbumReplyVO reply) {
		amapper.updateReplyCnt(reply.getAno(), 1); // ano에 1증가(댓글이 1개 추가되었으므로)
		return rmapper.insert(reply);
	}
	
	/* 댓글 조회 */
	@Override
	public AlbumReplyVO get(Long rno) {			
		return rmapper.read(rno);
	}
			
	/* 게시글에 달린 댓글 목록 조회(페이징) */
	@Override
	public AlbumReplyPageDTO getListPage(Criteria cri, Long ano) {
		return new AlbumReplyPageDTO(rmapper.getCountByAno(ano), rmapper.getListWithPaging(cri, ano));
	}	
	
	/* 댓글 수정 */
	@Override
	public int modify(AlbumReplyVO reply) {			
		return rmapper.update(reply);
	}
	
	/* 댓글 삭제 + 댓글수-1 */
	@Transactional
	@Override
	public int remove(Long rno) {
		AlbumReplyVO reply = rmapper.read(rno);
		amapper.updateReplyCnt(reply.getAno(), -1); // ano에 1감소(댓글이 1개 삭제되었으므로)
		
		return rmapper.delete(rno);
	}	
}
