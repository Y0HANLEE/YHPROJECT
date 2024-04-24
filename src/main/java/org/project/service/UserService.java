package org.project.service;

import java.util.List;

import org.project.domain.Criteria;
import org.project.domain.MyCriteria;
import org.project.domain.Album.AlbumReplyVO;
import org.project.domain.Album.AlbumVO;
import org.project.domain.Board.BoardReplyVO;
import org.project.domain.Board.BoardVO;
import org.project.domain.User.AuthVO;
import org.project.domain.User.UserVO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	//���
	public void join(UserVO user, AuthVO auth);	
	//��ȸ
	public UserVO read(String userid);	
	public AuthVO readAuth(String userid); // ����� ��� ��ȸ
	public int checkId(String userid); // ���̵� �ߺ� Ȯ��	
	public List<String> findId(String name, String email); // ���̵� ã��
	//��ȸ_Ư�� ���̵�� �ۼ��� �Խñ�/��� ã��
	public List<BoardVO> findBoardByUserid(MyCriteria cri); // �ϹݰԽñ�
	public List<AlbumVO> findAlbumByUserid(MyCriteria cri); // �����Խñ� 
	public List<BoardReplyVO> findBoardReplyByUserid(MyCriteria cri); // �Ϲݴ��
	public List<AlbumReplyVO> findAlbumReplyByUserid(MyCriteria cri); // �������
	public int getBoardCnt(String userid);
	public int getAlbumCnt(String userid);
	public int getBoardReplyCnt(String userid);
	public int getAlbumReplyCnt(String userid);
	
	//����
	public int modify(UserVO user);
	public void updatePw(String newPw, String userid, String oldPw); // ��й�ȣ ���� | ������� �� �����
	public int renewalPw(String randomPw, String userid, String email); // ��й�ȣ �ʱ�ȭ | ������� �� �������	
	//����
	public void remove(String userid);
	//Admin
	public List<UserVO> getUserList(Criteria cri); // ȸ�����
	public int getTotalUser(Criteria cri); // �� ȸ�� ��
	public int updateAuth(String auth, String userid); // ȸ���������
	
}
