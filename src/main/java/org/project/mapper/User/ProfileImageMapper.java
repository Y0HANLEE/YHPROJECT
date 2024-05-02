package org.project.mapper.User;

import org.project.domain.User.ProfileImageVO;

public interface ProfileImageMapper {
	//등록
	public void insert(ProfileImageVO profile);
	//조회
	public ProfileImageVO getProfileByUserid(String userid);
	//수정
	public int update(ProfileImageVO profile);	
	//삭제
	public int delete(String userid);
}
