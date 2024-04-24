package org.project.controller;

import org.project.service.IntroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/main/*")
public class MainController {	
    @Setter(onMethod_=@Autowired)
    private IntroService iservice;
	    
    @GetMapping({"/home","/intro"})
    public void introPage(Model model) {    	
    	model.addAttribute("home", iservice.read(1));
    	model.addAttribute("intro", iservice.read(2));
    }
}