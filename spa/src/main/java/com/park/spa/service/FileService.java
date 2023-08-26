package com.park.spa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.park.spa.common.FileUtil;
import com.park.spa.common.StringUtil;
import com.park.spa.common.session.UserSessionService;
import com.park.spa.mapper.BoardMapper;
import com.park.spa.mapper.FileMapper;
import com.park.spa.vo.BoardVo;
import com.park.spa.vo.FileVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
	
	@Autowired
	BoardMapper boardMapper;
	
	@Autowired
	FileMapper fileMapper;
	
	@Autowired
	UserSessionService userSessionService;
	
	@Autowired
	FileUtil fileUtil;

    @Transactional
    public void saveFiles(String brdIdx, List<FileVo> files) {
        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        
        for(FileVo file : files) {
            file.setFileForeignIdx(file.getFileForeignIdx());
        }
        
        fileMapper.saveFiles(files);
    }
    
    public void uploadFiles(BoardVo boardVo) {
    	if(boardVo.getBrdIdx() != null) {
    		String brdIdx = boardVo.getBrdIdx();
    		List<FileVo> files = fileUtil.uploadFiles(boardVo.getFileList());
    		
    		if(files != null) {
    			this.saveFiles(brdIdx, files);
    		}
    	}
    	
    }

}
