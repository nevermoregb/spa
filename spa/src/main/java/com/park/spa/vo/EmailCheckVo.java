package com.park.spa.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class EmailCheckVo {
	public String email;
	public String code;
	public int byTime;
	public boolean checked = false;
}
