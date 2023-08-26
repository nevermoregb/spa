package com.park.spa.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileVo {
    private String fileIdx;              // 파일 번호 (PK)
	private String fileForeignIdx;       // 게시글 번호 (FK)
	private String fileOrigName;    	 // 원본 파일명
	private String fileName;        	 // 저장 파일명
	private long size;                   // 파일 크기

	@Builder
    public FileVo(String originalName, String saveName, long size) {
        this.fileOrigName = originalName;
        this.fileName = saveName;
        this.size = size;
    }
}