package org.project.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginHandler implements AuthenticationSuccessHandler {
//로그인이 성공하고 어떻게 할 것인지 권한별로 지정.
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		List<String> roleNames = new ArrayList<String>(); //권한 모음
		
		authentication.getAuthorities().forEach(auth -> {
			roleNames.add(auth.getAuthority());
		});
		
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/");
			return;
		} // admin계정의 경우 
		
		if(roleNames.contains("ROLE_MANAGER")) {
			response.sendRedirect("/");
			return;
		} // manager계정의 경우
		
		if(roleNames.contains("ROLE_MEMBER")) {
			response.sendRedirect("/");
			return;
		} // member계정의 경우
		
		response.sendRedirect("/"); // 기본
	}
}
