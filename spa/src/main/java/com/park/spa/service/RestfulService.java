package com.park.spa.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.park.spa.vo.SubwayVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestfulService {
	
    @Autowired
    RestTemplateBuilder builder;
    
    @Autowired
    Environment env;
    
    /**
     * 지하철역 목록 조회
     * 
     * ex)
     * 서울시 노선별 지하철역 정보-전철역명 (http://openapi.seoul.go.kr:8088/(인증키)/xml/SearchSTNBySubwayLineInfo/1/5/ /서울역)
     * 서울시 노선별 지하철역 정보-1호선   (http://openapi.seoul.go.kr:8088/(인증키)/xml/SearchSTNBySubwayLineInfo/1/5/ / /1호선)
     * 
     * @param 노선
     * @return 노선별 지하철역
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public List<SubwayVo> getSubwayInfo(String lineNum) throws Exception {
    	
    	String line  	= lineNum != null ? lineNum : "";
        String domain	= env.getRequiredProperty("api.openapi.url");						
        String apiKey 	= env.getRequiredProperty("api.openapi.key");
        String url 		= domain + apiKey + "/json/SearchSTNBySubwayLineInfo/1/200/%20/%20/" + line;

        JSONObject response = builder.build().getForEntity(url, JSONObject.class).getBody();			//지하철역 정보 조회
        Map<String, Object> inMap = (Map<String, Object>) response.get("SearchSTNBySubwayLineInfo");	//지하철역 정보 리스트
        
        List<SubwayVo> list = new ArrayList<>();
        
        if(inMap != null) {
        	for(Map<String, String> obj : (ArrayList<Map<String, String>>) inMap.get("row")) {
        		SubwayVo vo = new SubwayVo();
        		
        		vo.setLineNum(obj.get("LINE_NUM"));
        		vo.setStation(obj.get("STATION_NM"));
        		vo.setFrCode(obj.get("FR_CODE"));
        		
        		list.add(vo);
        	}
        	
        	list.sort(Comparator.comparing(SubwayVo::getFrCode));	//지하철역 순서대로 정렬
        }
        
    	return list;
	}
    
}
