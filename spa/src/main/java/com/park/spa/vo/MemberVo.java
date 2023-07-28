package com.park.spa.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class MemberVo {
	public String userIdx;
	public String userId;
	public String userName;
	public String userPw;
	public String userAuth;
	public String regDate;	
	public String modDate;
	public String useage;
}
