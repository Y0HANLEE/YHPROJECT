package org.project.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.project.domain.User.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class CustomUser extends User{
	//UserVO를 User타입으로 변환 (userid→username, userpw→password, authList→authorities)	
	private static final long serialVersionUID = 1L;
	private UserVO user;
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	} 
	
	public CustomUser(UserVO vo) {		
		super(vo.getUserid(), vo.getUserpw(), vo.getAuthList().stream().map(auth->new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));		
		this.user = vo; 
	}	
}
