package com.edu.springboard.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.springboard.domain.Notice;
import com.edu.springboard.model.notice.NoticeService;

import oracle.jdbc.proxy.annotation.Post;

@RestController
@RequestMapping("/rest")
public class RestAPINoticeController {
	
	@Autowired
	private NoticeService noticeService;
	// REST를 준수하여 URL을 매핑해보자 (RESTful)
	
	// 목록요청
	@GetMapping("/notices")
	public List<Notice> selectAll() {
		return noticeService.selectAll();
	}
	
	// 상세보기
	// @GetMapping("/notices/구분값") {}는 디렉토리 구분이 아닌 변수로 생각하라는 뜻
	@GetMapping("/notices/{notice_idx}") 
	public Notice select(@PathVariable("notice_idx") int notice_idx) { // path 중에 변수가 껴있어 그 변수가 뭐야 
		return noticeService.select(notice_idx);
	}
	
	// 삭제하기
	@DeleteMapping("/notices/{notice_idx}") // {}안에 있는 것이 경로가 아님을 명시해야 함 @PathVariable
	public String del(@PathVariable("notice_idx") int notice_idx) {
		noticeService.delete(notice_idx);
		return "OK";
	}
	
	// 수정하기
	@PutMapping("/notices")
	public String edit(Notice notice) {
		noticeService.update(notice);
		return "OK";
	}
	
	// 등록하기
	@PostMapping("/notices")
	public String regist(Notice notice) {
		noticeService.insert(notice); 
		
		return "OK";
	}
}
