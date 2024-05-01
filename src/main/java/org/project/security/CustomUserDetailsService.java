package org.project.security;

import org.project.domain.User.UserVO;
import org.project.mapper.User.UserMapper;
import org.project.security.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	//사용자 정보 조회 및 처리에 관한 클래스
	@Setter(onMethod_=@Autowired)
	private UserMapper umapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		UserVO user = umapper.read(username);    
		log.warn("[CustomUserDetailsService]로그인유저----------------------------------"+username+",  "+user);
        return user == null ? null : new CustomUser(user);  
	}	
}
