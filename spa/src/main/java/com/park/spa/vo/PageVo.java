package com.park.spa.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class PageVo {
	public int page;
	public String searchType;
	public String searchKeyword;
	public String lineNum;
}
