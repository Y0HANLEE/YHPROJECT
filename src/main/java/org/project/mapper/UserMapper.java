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
	//등록
	public void insertUser(UserVO user);
	public void insertAuth(AuthVO auth);

	//조회
	public UserVO read(String userid); // 사용자 정보 조회
	public AuthVO readAuth(String userid); // 사용자 등급 조회
	public int checkId(String userid); // 아이디 중복검사
	public List<String> findId(@Param("name") String name, @Param("email") String email); // 아이디 찾기 | 한 사람이 여러 아이디를 갖는다는 가정
		
	//조회_내가 작성한 글/댓글
	public List<BoardVO> boardList(MyCriteria cri); // 일반게시판
	public List<AlbumVO> albumList(MyCriteria cri); // 사진게시판
	public List<BoardReplyVO> boardReplyList(MyCriteria cri); // 일반게시판 댓글
	public List<AlbumReplyVO> albumReplyList(MyCriteria cri); // 사진게시판 댓글
	public int getBoardCnt(String userid);
	public int getAlbumCnt(String userid);
	public int getBoardReplyCnt(String userid);
	public int getAlbumReplyCnt(String userid);	
	
	//수정
	public int update(UserVO user); // 회원정보 수정 : 연락처, 주소, 이메일
	public void updatePw(@Param("newPw") String newPw, @Param("userid") String userid, @Param("oldPw") String oldPw); // 비밀번호 수정 | @Param:newPw,oldPw를 xml에서 변수로써 사용하겠다.
	public int renewalPw(@Param("randomPw") String randomPw, @Param("userid") String userid, @Param("email") String email); // 비밀번호 초기화
	
	//삭제_회원탈퇴 : 추후 고민_탈퇴처리를 삭제할것이냐 enabled와 같은 방법으로 할 것이냐.
	public int delete(String userid); // 정보삭제
	public int deleteAuth(String userid); // 권한삭제
	public int deleteSession(String userid); // 자동로그인 기록 삭제
	
	//Admin
	public List<UserVO> getUserList(Criteria cri); // 회원목록조회
	public int getTotalUser(Criteria cri); // 총 회원수 파악
	public int updateAuth(@Param("auth")String auth, @Param("userid")String userid); // 회원 등급조정
	
}
