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
		
	/* 신규 사용자 등록 */
	@Transactional
	@Override
	public void join(UserVO user, AuthVO auth) {
		umapper.insertUser(user); // 사용자 정보 등록
		umapper.insertAuth(auth); // 권한 등록
		
		ProfileImageVO profile = user.getProfileImg();
		profile.setUserid(user.getUserid()); 
		pmapper.insert(profile);		
	}
		
	/* 사용자 정보 조회*/
	@Override
	public UserVO read(String userid) {
		return umapper.read(userid);
	}
	
	/* 사용자 등급 조회*/
	@Override
	public AuthVO readAuth(String userid) {
		return umapper.readAuth(userid);
	}
	
	/* 아이디 중복 확인 */
	@Override
	public int checkId(String userid) {
		return umapper.checkId(userid);
	}

	/* 아이디 찾기 */
	public List<String> findId(String name, String email) {
		return umapper.findId(name, email);
	}
	
	/* 내가 쓴 게시글(board) 찾기 */ 
	public List<BoardVO> findBoardByUserid(MyCriteria cri){
		return umapper.boardList(cri);
	}
	/* 내가 쓴 게시글(album) 찾기 */ 
	public List<AlbumVO> findAlbumByUserid(MyCriteria cri){
		return umapper.albumList(cri);
	}
	/* 내가 쓴 댓글(board) 찾기 */ 
	public List<BoardReplyVO> findBoardReplyByUserid(MyCriteria cri){
		return umapper.boardReplyList(cri);
	}
	/* 내가 쓴 댓글(album) 찾기 */ 
	public List<AlbumReplyVO> findAlbumReplyByUserid(MyCriteria cri){
		return umapper.albumReplyList(cri);
	}
	
	/* 내가 쓴 글 개수 */
	public int getBoardCnt(String userid) {
		return umapper.getBoardCnt(userid);
	}
	/* 내가 쓴 글 개수 */
	public int getAlbumCnt(String userid){
		return umapper.getAlbumCnt(userid);
	}
	/* 내가 쓴 댓글 개수 */
	public int getBoardReplyCnt(String userid){
		return umapper.getBoardReplyCnt(userid);
	}
	/* 내가 쓴 댓글 개수 */
	public int getAlbumReplyCnt(String userid){
		return umapper.getAlbumReplyCnt(userid);
	}	
	
	/* 프로필사진 조회 */
	@Override
	public ProfileImageVO getProfileByUserid(String userid){	
		return pmapper.getProfileByUserid(userid);
	}
	
	/* 사용자 정보 수정 */
	@Override
	public int modify(UserVO user) {
		ProfileImageVO profile = user.getProfileImg();
		profile.setUserid(user.getUserid());
		pmapper.update(profile);
		
		return umapper.update(user);
	}
	
	/* 비밀번호 수정 */
	@Override
    public void updatePw(String newPw, String userid, String oldPw) {
        umapper.updatePw(newPw, userid, oldPw);        
    }
	
	/* 비밀번호 초기화 */
	public int renewalPw(String randomPw, String userid, String email) {
		return umapper.renewalPw(randomPw, userid, email);
	}
	
	/* 사용자 & 사용자 권한 삭제 */
	@Transactional
	@Override
	public void remove(String userid) {	
		int authDel = umapper.deleteAuth(userid); // 사용자권한 삭제(사용자정보 fk충돌로 인해 먼저 삭제) >> 추후 DB에서 on delete cascade로 설정했으나 다른 방법으로 해결한 케이스로 남겨둠
		if(authDel > 0) {
			umapper.delete(userid); // 사용자정보 삭제
			umapper.deleteSession(userid); // 자동로그인 기록삭제
			pmapper.delete(userid); // 프로필사진 삭제
		}
	}	
		
	/* Admin_사용자목록조회 */
	public List<UserVO> getUserList(Criteria cri){
		return umapper.getUserList(cri);
	}
	
	/* Admin_총 회원 수 계산 */
	@Override
	public int getTotalUser(Criteria cri) {
		return umapper.getTotalUser(cri);
	} 

	/* Admin_사용자등급조정 */
	public int updateAuth(String auth, String userid) {
		return umapper.updateAuth(auth, userid);
	}
}
