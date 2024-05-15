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

	/* ��� + ÷������ ��� */
	@Transactional
	@Override
	public void register(AlbumVO album) {
		almapper.insertSelectKey(album);
		//÷������x
		if(album.getAttachList() == null || album.getAttachList().size() <= 0) {		
			return;
		} 				
		//÷������o
		album.getAttachList().forEach(attach -> {
			attach.setAno(album.getAno());
			amapper.insert(attach);
		});	
	}
	
	/* ��ȸ + ��ȸ�� ���� */
	@Override	
	public AlbumVO read(Long ano) {
		upHit(ano); //�Խñ� ��ȸ�� ��ȸ�� 1����
		return almapper.read(ano);
	}
	
	/* ��� ��ȸ(����¡+�˻�����) */
	@Override
	public List<AlbumVO> getList(Criteria cri){
		log.info("-----------------------"+almapper.getListWithPaging(cri));
		return almapper.getListWithPaging(cri);	
	}
	
	/* �Խù� ÷������ ��� ��ȸ */
	@Override
	public List<AlbumAttachVO> attachList(Long ano){
		return amapper.findByAno(ano);
	}
	
	/* �Խù� �� ��� */
	@Override
	public int totalCnt(Criteria cri) {
		return almapper.getTotalCnt(cri);
	}
	
	/* ���� + ÷������ ���� */
	@Transactional
	@Override	
	public int modify(AlbumVO album) {
		//÷�������� ���� ���, �ϴ� ��� ���� �� �߰� ó��
		int result = almapper.update(album);		
		
		amapper.deleteAll(album.getAno()); //����
		log.info("[albumService]--------------------------delete");
		
		if(result==1 && album.getAttachList()!=null && album.getAttachList().size()>0) {
			album.getAttachList().forEach(attach -> {
				attach.setAno(album.getAno());
				amapper.insert(attach); //����
				log.info("[albumService]--------------------------"+attach);
			});
		}
		
		return result;
	}
	
	/* ��ȸ�� ���� */
	public void upHit(Long ano) {
		almapper.upHit(ano);
	}	
	
	/* ���� + ÷������ ���� */
	@Override
	public int remove(Long ano) {
		amapper.deleteAll(ano); // ÷������ ����
		return almapper.delete(ano); // �Խñ� ����
	}
	
}
