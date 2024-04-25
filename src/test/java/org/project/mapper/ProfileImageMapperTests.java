package org.project.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.ProfileImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ProfileImageMapperTests {

	@Setter(onMethod_ = @Autowired)
	private ProfileImageMapper pmapper;
		
	/*프로필등록-가입시*/
	@Test
	public void testInsertProfile() {		
		ProfileImageVO profile = new ProfileImageVO();
		//테스트를 위해 임시로 작성
		profile.setUserid("qwerty");
		profile.setFileName("test2.jpg");
		profile.setFileType(true);
		profile.setUploadPath("C:\\upload");
		profile.setUuid("gengi;opehjiejafgqgoh");
		pmapper.insert(profile);
	}	
	
	/*프로필조회*/
	@Test
	public void testGetProfile() {
		pmapper.getProfileByUserid("qwerty");
	}
	
	/*프로필수정*/
	@Test
	public void testUpdateProfile() {
		ProfileImageVO profile = pmapper.getProfileByUserid("qwerty");		
		profile.setFileName("testUpdate.png");
		profile.setFileType(true);
		profile.setUploadPath("C:\\upload");
		profile.setUuid("gengi;opehjiejafgqgohda");
		pmapper.update(profile);
	}
	
	/*프로필초기화*/
	@Test
	public void testResetProfile() {
		ProfileImageVO profile = pmapper.getProfileByUserid("qwerty");
		pmapper.reset(profile);
	}
	
	/*프로필삭제-탈퇴시*/
	@Test
	public void testDeleteProfile() {
		ProfileImageVO profile = pmapper.getProfileByUserid("qwerty");
		pmapper.delete(profile.getUserid());
	}
}
