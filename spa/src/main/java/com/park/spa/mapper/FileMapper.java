package com.park.spa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.park.spa.vo.FileVo;

@Mapper
public interface FileMapper {

	void saveFiles(List<FileVo> files);
}
