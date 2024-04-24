package org.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.project.domain.Criteria;
import org.project.domain.MyCriteria;
import org.project.domain.Album.AlbumReplyVO;
import org.project.domain.Album.AlbumVO;
import org.project.domain.Board.BoardReplyVO;
import org.project.domain.Board.BoardVO;
import org.project.domain.User.AuthVO;
import org.project.domain.User.UserVO;

public interface UserMapper {
	//���
	public void insertUser(UserVO user);
	public void insertAuth(AuthVO auth);

	//��ȸ
	public UserVO read(String userid); // ����� ���� ��ȸ
	public AuthVO readAuth(String userid); // ����� ��� ��ȸ
	public int checkId(String userid); // ���̵� �ߺ��˻�
	public List<String> findId(@Param("name") String name, @Param("email") String email); // ���̵� ã�� | �� ����� ���� ���̵� ���´ٴ� ����
		
	//��ȸ_���� �ۼ��� ��/���
	public List<BoardVO> boardList(MyCriteria cri); // �ϹݰԽ���
	public List<AlbumVO> albumList(MyCriteria cri); // �����Խ���
	public List<BoardReplyVO> boardReplyList(MyCriteria cri); // �ϹݰԽ��� ���
	public List<AlbumReplyVO> albumReplyList(MyCriteria cri); // �����Խ��� ���
	public int getBoardCnt(String userid);
	public int getAlbumCnt(String userid);
	public int getBoardReplyCnt(String userid);
	public int getAlbumReplyCnt(String userid);	
	
	//����
	public int update(UserVO user); // ȸ������ ���� : ����ó, �ּ�, �̸���
	public void updatePw(@Param("newPw") String newPw, @Param("userid") String userid, @Param("oldPw") String oldPw); // ��й�ȣ ���� | @Param:newPw,oldPw�� xml���� �����ν� ����ϰڴ�.
	public int renewalPw(@Param("randomPw") String randomPw, @Param("userid") String userid, @Param("email") String email); // ��й�ȣ �ʱ�ȭ
	
	//����_ȸ��Ż�� : ���� ���_Ż��ó���� �����Ұ��̳� enabled�� ���� ������� �� ���̳�.
	public int delete(String userid); // ��������
	public int deleteAuth(String userid); // ���ѻ���
	public int deleteSession(String userid); // �ڵ��α��� ��� ����
	
	//Admin
	public List<UserVO> getUserList(Criteria cri); // ȸ�������ȸ
	public int getTotalUser(Criteria cri); // �� ȸ���� �ľ�
	public int updateAuth(@Param("auth")String auth, @Param("userid")String userid); // ȸ�� �������
	
}
