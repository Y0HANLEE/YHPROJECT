package home.starterpack;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.project.domain.IntroVO;
import org.project.domain.User.AuthVO;
import org.project.domain.User.ProfileImageVO;
import org.project.domain.User.UserVO;
import org.project.mapper.Intro.IntroMapper;
import org.project.mapper.User.ProfileImageMapper;
import org.project.mapper.User.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class AdminStarterPackTests {
	@Setter(onMethod_ = @Autowired)
	private UserMapper u;
	@Setter(onMethod_ = @Autowired)
	private ProfileImageMapper p;
	@Setter(onMethod_ = @Autowired)
	private IntroMapper i;
	@Setter(onMethod_ = @Autowired)
	BCryptPasswordEncoder bcrypt;
		
	/* ȸ�� ��� �׽�Ʈ */
	@Test
	public void testAdminStarterPack() {
		//1ȸ�� �ǽ�_admin����, home/introȭ�� �� ��� > Ŭ���̾�Ʈ�� ������ ����.
		//������� ��ü ����
		UserVO user = new UserVO();
		AuthVO auth = new AuthVO();
		IntroVO home = new IntroVO();
		IntroVO intro = new IntroVO();
		ProfileImageVO profile = new ProfileImageVO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//�����ڰ��� ����� ����		
		String id = "admin";
		String password = bcrypt.encode("1234");
		String name = "������";
		String email = "kevinyh18@gmail.com";
		String gender = "M"; //"M":���� "F":����
		String birth = "1900-01-01"; 
		//����/�Ұ�ȭ�� ����� ����
		String tt = "�����Դϴ�. �����Ҷ��� �ݵ�� �������ּ���";
		String ti = "������ �Ұ����Դϴ�.";
		String mt = "���� �����Դϴ�.";
		String mi = "���� �Ұ����Դϴ�.";
		String mc = "���� ��Ŀ ǥ�ñ�";
		String ad = "Ŭ���Ͻø� �ּҰ˻��� ����˴ϴ�.";
		String add = "���ּ��Դϴ�";
		String hi = "������ �ϴܺο� �� �����Դϴ�. ����ó Ȥ�� �ʿ��Ͻ� ������ �������ּ���";
		String ii = "���� ���� ������ �ִٸ� �����ּ���.";		
		//�����ڵ�ϼ���
		user.setUserid(id);
		auth.setUserid(id);			
		profile.setUserid(id);
		user.setUserpw(password);
		user.setName(name);
		user.setEmail(email);
		user.setGender(gender);
		try { Date date = sdf.parse(birth); user.setBirth(date); } catch (ParseException e) {e.printStackTrace();}		
		profile.setUuid("ed87212c-4e79-4813-be6c-8c73ac58ac33");
		profile.setFileName("Default-Profile.png");
		profile.setFileType(true);
		profile.setUploadPath("2024\\04\\27");
		//����ȭ�鳻�뼼��
		home.setTitle_title(tt);
		home.setTitle_intro(ti);
		home.setMap_title(mt);
		home.setMap_intro(mi);
		home.setMap_caption(mc);
		home.setMap_address(ad); 
		home.setMap_addressdetail(add); 
		home.setIntro(hi); 
		home.setBoardtype(1);
		//�Ұ�ȭ�鳻�뼼��
		intro.setTitle_title(tt);
		intro.setTitle_intro(ti);
		intro.setMap_title(mt);
		intro.setMap_intro(mi);
		intro.setMap_caption(mc);
		intro.setMap_address(ad); 
		intro.setMap_addressdetail(add); 
		intro.setIntro(ii); 
		intro.setBoardtype(2);
		//���
		u.insertUser(user); 				//ȸ�����
		u.insertAuth(auth); 				//������ID_�⺻���ѵ��
		u.updateAuth("ROLE_ADMIN", id); 	//������ID_�����ڵ������ ����
		p.insert(profile); 					//�⺻������ ���
		i.insert(home); 					//����ȭ�� ���
		i.insert(intro); 					//�Ұ�ȭ�� ���
		//Ȯ��
		log.info("[Admin Insert Account]--------- "+u.read(id));
		log.info("[Admin Insert Auth]------------ "+u.readAuth(id));
		log.info("[Admin Insert Profile]--------- "+p.getProfileByUserid(id));
		log.info("[Admin Insert HomePage]-------- "+i.read(1));
		log.info("[Admin Insert IntroPage]------- "+i.read(2));
	}	
}
