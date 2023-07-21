package com.park.spa.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.park.spa.service.MemberService;
import com.park.spa.vo.MemberVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/spa")
public class MainController {
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/main")
	public String main(Model model) {
		log.info("=============123123123123================================");
		
		List<MemberVo> memberList = memberService.getAllMemberList();
		model.addAttribute("memberList", memberList);
		
		return "index";
	}

}
