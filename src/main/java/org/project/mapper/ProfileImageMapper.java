package org.project.mapper;

import org.project.domain.ProfileImageVO;

public interface ProfileImageMapper {
	//��ȸ
	public void insert(ProfileImageVO profile);		
	//����
	public int update(ProfileImageVO profile);
	//����
	public int delete(ProfileImageVO profile);
}
