package org.project.mapper;

import org.project.domain.ProfileImageVO;

public interface ProfileImageMapper {
	//���
	public void insert(ProfileImageVO profile);
	//��ȸ
	public ProfileImageVO getProfileByUserid(String userid);
	//����
	public int update(ProfileImageVO profile);
	//����
	public int delete(ProfileImageVO profile);
}
