package com.park.spa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.park.spa.common.SessionUtil;
import com.park.spa.common.session.UserSessionService;
import com.park.spa.mapper.BoardMapper;
import com.park.spa.vo.BoardVo;
import com.park.spa.vo.CooVo;
import com.park.spa.vo.MemberVo;
import com.park.spa.vo.PageVo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {
	
	@Autowired
	BoardMapper boardMapper;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	UserSessionService userSessionService;

	public List<BoardVo> getBoardList() {
		return boardMapper.getBoardList();
	}

	public List<BoardVo> getBoardList(PageVo pageVo) {
		return boardMapper.getBoardList2(pageVo);
	}

	@Transactional(rollbackFor=Exception.class)
	public ModelAndView insertBoardContent(BoardVo boardVo, ModelAndView mv) throws Exception {
		MemberVo memberVo = (MemberVo) SessionUtil.getAttribute("MemberVo");
		boardVo.setBrdUserIdx(memberVo.getUserIdx());

		int result = boardMapper.insertBoardContent(boardVo);	//게시글 insert
		
		if(result == 1) {
			int result2 = this.insStoreInfo(boardVo);			//가게정보 insert
			if(boardVo.getCooVo() != null && result2 == 1) {
				CooVo cooVo = boardVo.getCooVo();
				String strIdx = boardVo.getStoreVo().getStrIdx();
				cooVo.setCooStrIdx(strIdx);
				
				this.insBoardMapInfo(cooVo);					//카카오맵 정보 insert
				
//				if(!boardVo.getFileList().isEmpty()) {
//					fileService.uploadFiles(boardVo);			//파일 인설트
//				}
			} else {
				throw new Exception();
			}
			
			PageHelper.startPage(1, 10);
			PageInfo<BoardVo> boardList = PageInfo.of(getBoardList(), 5);	//게시글 조회
			
		    mv.addObject("boardList", boardList);
		    mv.setViewName("boardListTemplate");
		} else {
			throw new Exception();
		}
		
		return mv;
	}

	public ModelAndView delBoardContent(BoardVo boardVo, ModelAndView mv) throws Exception {
		MemberVo memberVo = (MemberVo) SessionUtil.getAttribute("MemberVo");
		
		//글쓴이가 현 사용자와 같은지 확인
		if(memberVo.getUserIdx().equals(boardVo.getBrdUserIdx())) {
			int result = boardMapper.delBoardContent(boardVo);						//게시글 삭제
			
			if(result == 1) {
				int page = boardVo.getPageVo().getPage();
			    PageHelper.startPage(page, 10);
			    
			    PageInfo<BoardVo> boardList 
			    	= PageInfo.of(boardMapper.getBoardList2(boardVo.getPageVo()), 5);	//리스트 불러오기
			    
			    mv.addObject("boardList", boardList);
			    mv.setViewName("boardListTemplate");
			} else {
				log.debug("게시물 추가 오류");
				throw new Exception();
			}
		} else {
			mv.setStatus(HttpStatusCode.valueOf(499));								//유효하지 않은 요청입니다.
		}
		
		return mv;
	}
	
	/**
	 * 게시글 조회(읽기) 
	 *     + 조회수 증가
	 * 
	 * @param boardVo
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView getBoardContent2(BoardVo boardVo, ModelAndView mv) throws Exception {
		MemberVo memberVo = (MemberVo) SessionUtil.getAttribute("MemberVo");
		
		String brdUserIdx = boardVo.getBrdUserIdx();
		String userIdx = memberVo.getUserIdx();
		
		//작성자와 사용자가 틀리다면 조회수 증가
		if(!brdUserIdx.equals(userIdx)) {
			this.updateBoardCount(boardVo);						//조회수 증가
		}
		
		BoardVo vo = boardMapper.getBoardContent(boardVo);		//게시글 목록 조회
		
		mv.addObject("memberVo", memberVo);
		mv.addObject("boardVo", vo);
		mv.setViewName("board_read");
		
		return mv;
		
	}

	public ModelAndView getBoardContent(BoardVo boardVo, ModelAndView mv) throws Exception {
		MemberVo memberVo = (MemberVo) SessionUtil.getAttribute("MemberVo");
		
		//글쓴이가 현 사용자와 같은지 확인
		if(memberVo.getUserIdx().equals(boardVo.getBrdUserIdx())) {
			BoardVo vo = boardMapper.getBoardContent(boardVo);
			mv.addObject("boardVo", vo);
			mv.setViewName("board_write");
			
		} else {
			log.debug("게시물 수정 권한이 없습니다.");
			mv.setStatus(HttpStatusCode.valueOf(499));						//유효하지 않은 요청입니다.
		}
		
		return mv;
	}

	public ModelAndView updBoardContent(BoardVo boardVo, ModelAndView mv) throws Exception {
		if (userSessionService.getUser() == null) {
			mv.setStatus(HttpStatusCode.valueOf(401));						
		} else {
			MemberVo memberVo = (MemberVo) SessionUtil.getAttribute("MemberVo");
			
			//글쓴이가 현 사용자와 같은지 확인
			if(memberVo.getUserIdx().equals(boardVo.getBrdUserIdx())) {
				boardVo.setBrdUserIdx(memberVo.getUserIdx());
				int result = boardMapper.updateBoardContent(boardVo);					//게시글 업데이트
				
				if(result == 1) {
					int page = boardVo.getPageVo().getPage();
				    PageHelper.startPage(page, 10);
				    
				    PageInfo<BoardVo> boardList 
				    	= PageInfo.of(boardMapper.getBoardList2(boardVo.getPageVo()), 5);	//리스트 불러오기
				    
				    mv.addObject("boardList", boardList);
				    mv.setViewName("boardListTemplate");
				    
				} else {
					log.debug("게시물 업데이트 오류");
					mv.setStatus(HttpStatusCode.valueOf(498));							//저장에 실패
				}
			} else {
				log.debug("게시물 수정 권한이 없습니다.");
				mv.setStatus(HttpStatusCode.valueOf(499));								//유효하지 않은 요청
			}
		}
		
		return mv;
	}
	
	/**
	 * 조회수 추가
	 * 
	 * @return 성공:1
	 */
	public int updateBoardCount(BoardVo boardVo) {
		return boardMapper.updateBoardCount(boardVo);
	}
	
	/**
	 * 조회수 쿠기 설정 (사용중지)
	 * 
	 * @param request
	 * @param response
	 * @param boardVo
	 * @throws Exception
	 */
	public void setCookie(HttpServletRequest request, HttpServletResponse response, BoardVo boardVo) throws Exception {
		MemberVo memberVo = (MemberVo) SessionUtil.getAttribute("MemberVo");
		Cookie[] cookies = request.getCookies();
		Cookie oldCookie = null;
		String brdIdx = boardVo.getBrdIdx();
		String userIdx = memberVo.getUserIdx(); 
		
		//사용자 쿠키 조회
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("countView")) {
					oldCookie = cookie;
				}
			}
		}
		
		//countView 쿠키가 있다면
		if (oldCookie != null) {
			//해당 쿠키에 게시판번호 없으면 추가
			if (!oldCookie.getValue().contains("[user"+ userIdx + "_" + brdIdx +"]")) {
				oldCookie.setValue(oldCookie.getValue() + "_[user"+ userIdx + "_" + brdIdx +"]");
				oldCookie.setPath("/");
				oldCookie.setMaxAge(60 * 60 * 24);
				response.addCookie(oldCookie);
				
				this.updateBoardCount(boardVo);		//조회수 추가
			}
		} else {
			Cookie newCookie = new Cookie("countView", "[user"+ userIdx + "_" + brdIdx + "]");
			newCookie.setPath("/");
			newCookie.setMaxAge(60 * 60 * 24);
			response.addCookie(newCookie);
			
			this.updateBoardCount(boardVo);			//조회수 추가
		}
	}
	
	/**
	 * 게시글에 입력 된 kakaomap 정보를 db에 넣는다. 
	 * @param boardVo 
	 */
	public int insBoardMapInfo(CooVo cooVo) {
		return boardMapper.insBoardMapInfo(cooVo);
	}
	
	/**
	 * 게시글에 입력 된 가게정보를 db에 적재
	 * @param boardVo
	 * @return
	 */
	public int insStoreInfo(BoardVo boardVo) {
		return boardMapper.insStoreInfo(boardVo);
	};

}
