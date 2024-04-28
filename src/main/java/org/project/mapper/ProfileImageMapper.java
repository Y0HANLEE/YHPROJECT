package org.project.mapper;

import org.project.domain.User.ProfileImageVO;

public interface ProfileImageMapper {
	//���
	public void insert(ProfileImageVO profile);
	//��ȸ
	public ProfileImageVO getProfileByUserid(String userid);
	//����
	public int update(ProfileImageVO profile);
	//�ʱ�ȭ
	public int reset(ProfileImageVO profile);
	//����
	public int delete(String userid);
}
