package com.park.spa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.park.spa.vo.MemberVo;

@Mapper
public interface MemberMapper {
	List<MemberVo> getAllMemberList();

	MemberVo getMemberDetail(String userId);

}
