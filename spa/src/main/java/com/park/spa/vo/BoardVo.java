package com.park.spa.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardVo {
	private String brdIdx;
	private String brdUserIdx;
	private String userName;
	private String title;
	private String content;
	private String viewCount;
	private String regDate;	
	private String modDate;
	private String modUserIdx;
	private String useage;
	
	private PageVo pageVo;
	private StoreVo storeVo;
	private CooVo cooVo;
	private List<MultipartFile> fileList;
	
	//Json String 으로 들어온 cooVo객체가 컨트롤러에서 자동으로 매핑안되어 set처리 
	public void setCooVo(String cooVo) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.cooVo = objectMapper.readValue(cooVo, CooVo.class);
	}
	
	public void setStoreVo(String storeVo) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		this.storeVo = objectMapper.readValue(storeVo, StoreVo.class);
	}
	
}
