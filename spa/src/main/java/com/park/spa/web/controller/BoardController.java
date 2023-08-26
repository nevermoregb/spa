package com.park.spa.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.park.spa.common.SessionUtil;
import com.park.spa.common.constant.ConstantList;
import com.park.spa.common.session.UserSessionService;
import com.park.spa.service.BoardService;
import com.park.spa.service.MemberService;
import com.park.spa.service.RestfulService;
import com.park.spa.vo.BoardVo;
import com.park.spa.vo.CooVo;
import com.park.spa.vo.MemberVo;
import com.park.spa.vo.PageVo;
import com.park.spa.vo.SubwayVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	UserSessionService userSessionService;
	
	@Autowired
	RestfulService restfulService;
	
	
	/**
	 * 게시판 목록 조회
	 * 
	 * @param pageVo
	 * @param mv
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/list")
	public ModelAndView boardList(@RequestBody PageVo pageVo, ModelAndView mv) throws Exception {
		if (userSessionService.getUser() == null) {
			mv.setStatus(HttpStatusCode.valueOf(401));
		} else {
			int page = pageVo.getPage();
		    PageHelper.startPage(page, 10);
		    
		    PageInfo<BoardVo> boardList = PageInfo.of(boardService.getBoardList(pageVo), 5);	//게시판 리스트( +페이징 +검색조건)
		    mv.addObject("boardList", boardList);

		    mv.setViewName("boardListTemplate");
			
		}
	    
		return mv;
	}
	
	/**
	 * 게시글 작성화면(쓰기)
	 * 
	 * @param mv
	 * @return 
	 * @throws Exception
	 */
	@PostMapping("/write")
	public ModelAndView boardWrite(ModelAndView mv) throws Exception {
	    if (userSessionService.getUser() == null) {
	    	mv.setStatus(HttpStatusCode.valueOf(401));
	    } else {
	    	MemberVo memberVo = (MemberVo) SessionUtil.getAttribute("MemberVo");
	    	mv.addObject("memberVo", memberVo);
		    mv.addObject("subwayLines", ConstantList.SUBWAY_LINES);	//지하철역 목록
	    	mv.setViewName("board_write");
	    }
	    
		return mv;
	}
	
	/**
	 * 게시글 상세화면(읽기) + 조회수
	 * 
	 * @param mv
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/read")
	public ModelAndView boardRead(@RequestBody BoardVo boardVo, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (userSessionService.getUser() == null) {
			mv.setStatus(HttpStatusCode.valueOf(401));
		} else {
//			boardService.setCookie(request, response, boardVo);			//조회수 증가      => 230804 조회수 정책 변경: 사용자가 본인이 아닐경우 조회수 증가(쿠키x)
			mv = boardService.getBoardContent2(boardVo, mv);			//게시글 조회
		}
		
		return mv;
	}
	
	/**
	 * 게시글 작성 insert
	 * 
	 * @param boardVo
	 * @param mv
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/insert")
	public ModelAndView boardInsert(@RequestBody BoardVo boardVo, ModelAndView mv) throws Exception {
		if (userSessionService.getUser() == null) {
			mv.setStatus(HttpStatusCode.valueOf(401));
		} else {
			mv = boardService.insertBoardContent(boardVo, mv);
		}
		
		return mv;
	}
	
	/**
	 * 게시글 삭제
	 * 
	 * @param boardVo
	 * @param mv
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete")
	public ModelAndView boardDelete(@RequestBody BoardVo boardVo, ModelAndView mv) throws Exception {
		if (userSessionService.getUser() == null) {
			mv.setStatus(HttpStatusCode.valueOf(401));
		} else {
			mv = boardService.delBoardContent(boardVo, mv);
		}
		
		return mv;
	}
	
	/**
	 * 게시글 수정화면
	 * 
	 * @param boardVo
	 * @param mv
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/updContent")
	public ModelAndView boardUpdateView(@RequestBody BoardVo boardVo, ModelAndView mv) throws Exception {
		if (userSessionService.getUser() == null) {
			mv.setStatus(HttpStatusCode.valueOf(401));
		} else {
			mv = boardService.getBoardContent(boardVo, mv);
		}
		
		return mv;
	}
	
	/**
	 * 게시글 업데이트
	 * 
	 * @param boardVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
	public ModelAndView boardUpdate(@RequestBody BoardVo boardVo, ModelAndView mv) throws Exception {
		return boardService.updBoardContent(boardVo, mv);
	}
	
	/**
	 * 지하철역 조회
	 * 
	 * @param PageVo
	 * @return 
	 * @throws Exception
	 */
	@PostMapping("/getStations")
	public @ResponseBody List<SubwayVo> getStations(@RequestBody PageVo pageVo, ModelAndView mv) throws Exception {
		return restfulService.getSubwayInfo(pageVo.getLineNum());
		
//	    List<SubwayVo> subwayList 	= restfulService.getSubwayInfo(pageVo.getLineNum());	//지하철역 리스트
//	    mv.addObject("subwayList", subwayList);
	}
}
