package org.project.mapper;

import org.project.domain.ProfileImageVO;

public interface ProfileImageMapper {
	//조회
	public void insert(ProfileImageVO profile);		
	//수정
	public int update(ProfileImageVO profile);
	//수정
	public int delete(ProfileImageVO profile);
}
