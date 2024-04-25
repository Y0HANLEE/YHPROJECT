package org.project.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.project.domain.User.UserVO;
import org.project.security.domain.CustomUser;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;

@Profile("test")
@RequiredArgsConstructor
public class TestUserDetailsService implements UserDetailsService{
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		UserVO user = new UserVO();    
		user.setUserid("mgr1234");
		user.setUserpw("qwe12#$");
		user.setName("이요한");
		user.setGender("M");		
		user.setEmail("kevinyh@naver.com");
		user.setPhone("010-7133-2105");		
		user.setZonecode("14258");
		user.setAddress("대전광역시 중구 현백로 257");
		user.setAddressDetail("1층");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {                    
        	Date date = sdf.parse("1990-07-03");
            user.setBirth(date); 
        } catch (ParseException e) {          
        	e.printStackTrace();
        }
        
        return new CustomUser(user);		
	}
}
