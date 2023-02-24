package com.edu.mvc3.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Gallery {
	private int gallery_idx;
	private String title;
	private String writer;
	private String content;
	private String regdate;
	private int hit;
	private String filename;
	private MultipartFile file; // 바이너리 파일을 받는 객체(여기 보관)
	// 단, html 파라미터명과 일치해야 함 따라서 multil -> file
	
}


