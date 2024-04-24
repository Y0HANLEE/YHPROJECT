package org.project.controller;

import org.project.domain.Criteria;
import org.project.domain.PageDTO;
import org.project.domain.User.AuthVO;
import org.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Setter;

@Controller
@RequestMapping("/manager/*")
public class ManagerController {
	
	@Setter(onMethod_=@Autowired)
	private UserService uservice;
	
	/* 게시글 목록 화면 (LIST) : 페이징 */
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {	
		AuthVO auth = new AuthVO();
		model.addAttribute("auth", auth);
		model.addAttribute("list", uservice.getUserList(cri));		
		model.addAttribute("pageMaker", new PageDTO(cri, uservice.getTotalUser(cri)));
	}
}
