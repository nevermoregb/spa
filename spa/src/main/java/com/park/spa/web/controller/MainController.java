package com.park.spa.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.park.spa.common.SessionUtil;
import com.park.spa.common.constant.ConstantList;
import com.park.spa.service.BoardService;
import com.park.spa.service.MemberService;
import com.park.spa.vo.BoardVo;
import com.park.spa.vo.MemberVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/spa")
public class MainController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BoardService boardService;
	
	/**
	 * 메인페이지
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/main")
//	public String main(Model model, @AuthenticationPrincipal CustomUser customUser) throws Exception {
	public String main(Model model) throws Exception {
		Map<String, Object> userMap = new HashMap<>();
		MemberVo vo = null;
		
	    PageHelper.startPage(1, 10);
	    PageInfo<BoardVo> boardList = PageInfo.of(boardService.getBoardList(), 5);		//게시판 리스트( +페이징 )
	    vo = (MemberVo) SessionUtil.getAttribute("MemberVo");							//사용자 정보
	    
	    if(vo != null) {
	    	userMap.put("idx", vo.getUserIdx());
	    	userMap.put("name", vo.getUserName());
	    }
	    
	    model.addAttribute("subwayLines", ConstantList.SUBWAY_LINES);	//지하철역 목록
	    model.addAttribute("boardList", boardList);
	    model.addAttribute("userMap", userMap);
		
		return "board";
	}
}
