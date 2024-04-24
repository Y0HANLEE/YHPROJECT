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
//�α����� �����ϰ� ��� �� ������ ���Ѻ��� ����.
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		List<String> roleNames = new ArrayList<String>(); //���� ����
		
		authentication.getAuthorities().forEach(auth -> {
			roleNames.add(auth.getAuthority());
		});
		
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/");
			return;
		} // admin������ ��� 
		
		if(roleNames.contains("ROLE_MANAGER")) {
			response.sendRedirect("/");
			return;
		} // manager������ ���
		
		if(roleNames.contains("ROLE_MEMBER")) {
			response.sendRedirect("/");
			return;
		} // member������ ���
		
		response.sendRedirect("/"); // �⺻
	}
}
