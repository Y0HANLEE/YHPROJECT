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
	//등록
	public void join(UserVO user, AuthVO auth);	
	//조회
	public UserVO read(String userid);	
	public AuthVO readAuth(String userid); // 사용자 등급 조회
	public int checkId(String userid); // 아이디 중복 확인	
	public List<String> findId(String name, String email); // 아이디 찾기
	//조회_특정 아이디로 작성된 게시글/댓글 찾기
	public List<BoardVO> findBoardByUserid(MyCriteria cri); // 일반게시글
	public List<AlbumVO> findAlbumByUserid(MyCriteria cri); // 사진게시글 
	public List<BoardReplyVO> findBoardReplyByUserid(MyCriteria cri); // 일반댓글
	public List<AlbumReplyVO> findAlbumReplyByUserid(MyCriteria cri); // 사진댓글
	public int getBoardCnt(String userid);
	public int getAlbumCnt(String userid);
	public int getBoardReplyCnt(String userid);
	public int getAlbumReplyCnt(String userid);
	
	//수정
	public int modify(UserVO user);
	public void updatePw(String newPw, String userid, String oldPw); // 비밀번호 수정 | 기존비번 → 새비번
	public int renewalPw(String randomPw, String userid, String email); // 비밀번호 초기화 | 기존비번 → 난수비번	
	//삭제
	public void remove(String userid);
	//Admin
	public List<UserVO> getUserList(Criteria cri); // 회원목록
	public int getTotalUser(Criteria cri); // 총 회원 수
	public int updateAuth(String auth, String userid); // 회원등급조정
	
}
