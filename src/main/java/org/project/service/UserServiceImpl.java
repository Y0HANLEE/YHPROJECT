package org.project.service;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.MyCriteria;
import org.project.domain.Album.AlbumReplyVO;
import org.project.domain.Album.AlbumVO;
import org.project.domain.Board.BoardReplyVO;
import org.project.domain.Board.BoardVO;
import org.project.domain.User.AuthVO;
import org.project.domain.User.ProfileImageVO;
import org.project.domain.User.UserVO;
import org.project.mapper.ProfileImageMapper;
import org.project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{	
	@Setter(onMethod_ = @Autowired)
	private UserMapper umapper;
	
	@Setter(onMethod_ = @Autowired)
	private ProfileImageMapper pmapper;
		
	/* �ű� ����� ��� */
	@Transactional
	@Override
	public void join(UserVO user, AuthVO auth) {
		umapper.insertUser(user); // ����� ���� ���
		umapper.insertAuth(auth); // ���� ���
		
		ProfileImageVO profile = user.getProfileImg();
		profile.setUserid(user.getUserid()); 
		pmapper.insert(profile);		
	}
		
	/* ����� ���� ��ȸ*/
	@Override
	public UserVO read(String userid) {
		return umapper.read(userid);
	}
	
	/* ����� ��� ��ȸ*/
	@Override
	public AuthVO readAuth(String userid) {
		return umapper.readAuth(userid);
	}
	
	/* ���̵� �ߺ� Ȯ�� */
	@Override
	public int checkId(String userid) {
		return umapper.checkId(userid);
	}

	/* ���̵� ã�� */
	public List<String> findId(String name, String email) {
		return umapper.findId(name, email);
	}
	
	/* ���� �� �Խñ�(board) ã�� */ 
	public List<BoardVO> findBoardByUserid(MyCriteria cri){
		return umapper.boardList(cri);
	}
	/* ���� �� �Խñ�(album) ã�� */ 
	public List<AlbumVO> findAlbumByUserid(MyCriteria cri){
		return umapper.albumList(cri);
	}
	/* ���� �� ���(board) ã�� */ 
	public List<BoardReplyVO> findBoardReplyByUserid(MyCriteria cri){
		return umapper.boardReplyList(cri);
	}
	/* ���� �� ���(album) ã�� */ 
	public List<AlbumReplyVO> findAlbumReplyByUserid(MyCriteria cri){
		return umapper.albumReplyList(cri);
	}
	
	/* ���� �� �� ���� */
	public int getBoardCnt(String userid) {
		return umapper.getBoardCnt(userid);
	}
	/* ���� �� �� ���� */
	public int getAlbumCnt(String userid){
		return umapper.getAlbumCnt(userid);
	}
	/* ���� �� ��� ���� */
	public int getBoardReplyCnt(String userid){
		return umapper.getBoardReplyCnt(userid);
	}
	/* ���� �� ��� ���� */
	public int getAlbumReplyCnt(String userid){
		return umapper.getAlbumReplyCnt(userid);
	}	
	
	/* �����ʻ��� ��ȸ */
	@Override
	public ProfileImageVO getProfileByUserid(String userid){	
		return pmapper.getProfileByUserid(userid);
	}
	
	/* ����� ���� ���� */
	@Override
	public int modify(UserVO user) {
		ProfileImageVO profile = user.getProfileImg();
		profile.setUserid(user.getUserid());
		pmapper.update(profile);
		
		return umapper.update(user);
	}
	
	/* ��й�ȣ ���� */
	@Override
    public void updatePw(String newPw, String userid, String oldPw) {
        umapper.updatePw(newPw, userid, oldPw);        
    }
	
	/* ��й�ȣ �ʱ�ȭ */
	public int renewalPw(String randomPw, String userid, String email) {
		return umapper.renewalPw(randomPw, userid, email);
	}
	
	/* ����� & ����� ���� ���� */
	@Transactional
	@Override
	public void remove(String userid) {	
		int authDel = umapper.deleteAuth(userid); // ����ڱ��� ����(��������� fk�浹�� ���� ���� ����) >> ���� DB���� on delete cascade�� ���������� �ٸ� ������� �ذ��� ���̽��� ���ܵ�
		if(authDel > 0) {
			umapper.delete(userid); // ��������� ����
			umapper.deleteSession(userid); // �ڵ��α��� ��ϻ���
			pmapper.delete(userid); // �����ʻ��� ����
		}
	}	
		
	/* Admin_����ڸ����ȸ */
	public List<UserVO> getUserList(Criteria cri){
		return umapper.getUserList(cri);
	}
	
	/* Admin_�� ȸ�� �� ��� */
	@Override
	public int getTotalUser(Criteria cri) {
		return umapper.getTotalUser(cri);
	} 

	/* Admin_����ڵ������ */
	public int updateAuth(String auth, String userid) {
		return umapper.updateAuth(auth, userid);
	}
}
