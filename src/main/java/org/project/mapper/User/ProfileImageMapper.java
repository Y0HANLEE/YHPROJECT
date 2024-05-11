package org.project.mapper.User;

import java.util.List;

import org.project.domain.User.ProfileImageVO;

public interface ProfileImageMapper {
	//등록
	public void insert(ProfileImageVO profile);
	//조회
	public ProfileImageVO getProfileByUserid(String userid);
	public List<ProfileImageVO> getOldFiles(); // 하루전 첨부파일목록 조회 | 비교 후 삭제를 위함
	//수정
	public int update(ProfileImageVO profile);	
	//삭제
	public int delete(String userid);	
}
