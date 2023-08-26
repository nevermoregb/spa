package com.park.spa.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.park.spa.vo.BoardVo;
import com.park.spa.vo.CooVo;
import com.park.spa.vo.MemberVo;
import com.park.spa.vo.PageVo;

@Mapper
public interface BoardMapper {

	List<BoardVo> getBoardList();

	List<BoardVo> getBoardList2(PageVo pageVo);

	int insertBoardContent(BoardVo boardVo);

	BoardVo getBoardContent(BoardVo boardVo);

	int delBoardContent(BoardVo boardVo);

	int updateBoardContent(BoardVo boardVo);

	int updateBoardCount(BoardVo boardVo);

	int insBoardMapInfo(CooVo cooVo);

	int insStoreInfo(BoardVo boardVo);

}
