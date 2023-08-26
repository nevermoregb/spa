package com.park.spa.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.park.spa.vo.MemberVo;

@Mapper
public interface MemberMapper {
	List<MemberVo> getAllMemberList();

	MemberVo getMemberDetail(String userId);

	int isMemeber(String userEmail);

	int joinMember(MemberVo memberVo);

	int isNameUsed(String userName);

	int updatePassword(MemberVo memberVo);

}
