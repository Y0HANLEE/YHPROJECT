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
	
	/* ��� ��� + ��ۼ�+1 */
	@Transactional
	@Override
	public int register(AlbumReplyVO reply) {
		amapper.updateReplyCnt(reply.getAno(), 1); // ano�� 1����(����� 1�� �߰��Ǿ����Ƿ�)
		return rmapper.insert(reply);
	}
	
	/* ��� ��ȸ */
	@Override
	public AlbumReplyVO get(Long rno) {			
		return rmapper.read(rno);
	}
			
	/* �Խñۿ� �޸� ��� ��� ��ȸ(����¡) */
	@Override
	public AlbumReplyPageDTO getListPage(Criteria cri, Long ano) {
		return new AlbumReplyPageDTO(rmapper.getCountByAno(ano), rmapper.getListWithPaging(cri, ano));
	}	
	
	/* ��� ���� */
	@Override
	public int modify(AlbumReplyVO reply) {			
		return rmapper.update(reply);
	}
	
	/* ��� ���� + ��ۼ�-1 */
	@Transactional
	@Override
	public int remove(Long rno) {
		AlbumReplyVO reply = rmapper.read(rno);
		amapper.updateReplyCnt(reply.getAno(), -1); // ano�� 1����(����� 1�� �����Ǿ����Ƿ�)
		
		return rmapper.delete(rno);
	}	
}
