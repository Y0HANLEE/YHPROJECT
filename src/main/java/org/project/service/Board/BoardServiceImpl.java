package org.project.service.Board;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.Board.BoardAttachVO;
import org.project.domain.Board.BoardVO;
import org.project.mapper.Board.BoardAttachMapper;
import org.project.mapper.Board.BoardMapper;
import org.project.mapper.Board.BoardReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class BoardServiceImpl implements BoardService{
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper bmapper;

	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper amapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardReplyMapper rmapper;
	
	/* �Խñ� ��� + ÷������(tx) */
	@Transactional
	@Override
	public void register(BoardVO board) {
		bmapper.insertSelectKey(board);		
		//÷������x
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		} 		
		//÷������o
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno()); // attach�� bno�� board�� bno�� ����
			amapper.insert(attach); // ������ attach�� bno������ amapper�� ���� ÷������ ���
		});	
	}
	
	/* �Խñ� ��ȸ */
	@Override
	public BoardVO get(Long bno) {
		upHit(bno); // �Խñ� ��ȸ�� ��ȸ�� 1 ����
		return bmapper.read(bno);
	}
	
	/* �Խñ� ��� ��ȸ(����¡) */
	@Override
	public List<BoardVO> getList(Criteria cri){
		return bmapper.getListWithPaging(cri);
	}
	
	/* ÷������ ��� ��ȸ */
	@Override
	public List<BoardAttachVO> getAttachList(Long bno){	
		return amapper.findByBno(bno);
	}
	
	/* �� �Խù� �� ��� */
	@Override
	public int getTotal(Criteria cri) {
		return bmapper.getTotalCount(cri);
	}
	
	/* �Խù� �̵� */
	@Override
	public BoardVO move(Long bno) {
		return bmapper.move(bno);
	}
		
	/* �Խñ� ���� + ÷������ ����(tx) */
	@Transactional
	@Override
	public boolean modify(BoardVO board) {
		// ÷�������� ���� : ��ü���� �� �ٽ� ���
		amapper.deleteAll(board.getBno()); // ���� ����
		
		boolean modifyResult = bmapper.update(board) == 1;
		log.info("[BoardService]--------------------------delete");
		
		if(modifyResult && board.getAttachList()!=null && board.getAttachList().size()>0) {
			board.getAttachList().forEach(attach -> {
				attach.setBno(board.getBno());
				amapper.insert(attach); // �ٽ� ���
				log.info("[BoardService]--------------------------"+attach);
			});
		}
		
		return modifyResult;
	}
	
	/* ��ȸ�� ���� */
	@Override
	public void upHit(Long bno) {
		bmapper.upHit(bno);
	}
	
	/* �Խñ� ���� + ÷������ ����(tx) */
	@Transactional
	@Override
	public boolean remove(Long bno) {
		amapper.deleteAll(bno); // �Խù� ������ ÷�����ϵ� �Բ� �����		
		return bmapper.delete(bno) == 1;
	}
}
