package org.project.mapper.User;

import org.project.domain.User.ProfileImageVO;

public interface ProfileImageMapper {
	//���
	public void insert(ProfileImageVO profile);
	//��ȸ
	public ProfileImageVO getProfileByUserid(String userid);
	//����
	public int update(ProfileImageVO profile);	
	//����
	public int delete(String userid);
}
