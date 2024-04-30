package org.project.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.project.domain.Criteria;
import org.project.service.IntroService;
import org.project.service.Album.AlbumReplyService;
import org.project.service.Album.AlbumService;
import org.project.service.Board.BoardReplyService;
import org.project.service.Board.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.Setter;

@Controller
public class HomeController {
	@Setter(onMethod_=@Autowired)
	private BoardService bservice;
	
	@Setter(onMethod_=@Autowired)
	private AlbumService alservice;
	
	@Setter(onMethod_=@Autowired)
	private BoardReplyService brservice;
	
	@Setter(onMethod_=@Autowired)
	private AlbumReplyService arservice;
	
	@Setter(onMethod_=@Autowired)
	private IntroService iservice;	
	
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /* È¨È­¸é */    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Criteria cri, Model model) {
        logger.info("Welcome YH Project! The client locale is {}.", locale);        
        String formDate = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale).format(new Date());        
        model.addAttribute("serverTime", formDate);
    	
        Criteria criteria = new Criteria(1,5);        
        model.addAttribute("boardList", bservice.getList(criteria)); 
        model.addAttribute("albumList", alservice.getList(criteria));
        model.addAttribute("boardReplyList", brservice.getListAll(criteria)); 
        model.addAttribute("albumReplyList", arservice.getListAll(criteria));
        model.addAttribute("home", iservice.read(1));    	
        
        return "main/home";
    }     
}