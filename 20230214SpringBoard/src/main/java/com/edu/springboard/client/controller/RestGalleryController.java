package com.edu.springboard.client.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.edu.springboard.domain.Gallery;
import com.edu.springboard.exception.FileUploadException;
import com.edu.springboard.exception.GalleryException;
import com.edu.springboard.exception.PhotoException;
import com.edu.springboard.model.gallery.GalleryService;
import com.google.gson.Gson;

// RestController일 경우, 모든 메서드에 @ResponseBody를 생략가능
@RestController
public class RestGalleryController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private GalleryService galleryService;
	
	// 갤러리 업로드 요청처리
	// public ModelAndView regist(Gallery gallery, HttpServletRequest request) {
	@PostMapping("/gallery/regist")
	@ResponseBody // 메서드의 반환값을 jsp로 매핑하지 말고 순수 데이터로 처리하여 응답정보로 보낸다
	// 비동기 방식으로 데이터만 원할 때 씀 (페이지가 아니라)
	public String regist(Gallery gallery, HttpServletRequest request) {
		MultipartFile[] photo = gallery.getPhoto();
		
		for(int i=0; i<photo.length; i++) {
		logger.info("업로드 된 파일은 " + photo[i].getOriginalFilename());
		}
		
		// 3단계 : 일 시키기 (글 등록) DB + file
		HttpSession httpSession = request.getSession();
		ServletContext application = httpSession.getServletContext();
		String realPath = application.getRealPath("/resources/data/");
		
		logger.info(realPath); // 파일의 경로 확인을 위함
		
		galleryService.regist(gallery,realPath);

		// return new ModelAndView("redirect:/gallery/list");
		return "OK";
	}	
	// 비동기 목록 요청 처리 (주요 클라이언트 : web=ajax / app=android )
	@GetMapping("/rest/gallery/list")
	// public String getList() {
	public List<Gallery> getList() {
		// 3단계 : 일 시키기 
		List<Gallery> galleryList = galleryService.selectAll();
		
		// Gson gson = new Gson();
		// String jsonList = gson.toJson(galleryList);
		//  -> mapping json(Jackson Databind) 

		// return jsonList;
		return galleryList;
	}
	
	// 갤러리 한 건 가져오기
	@GetMapping("/rest/gallery/detail")
	public Gallery getDetail(int gallery_idx) {
		
		// 3단계 : 일 시키기
		Gallery gallery = galleryService.select(gallery_idx);
		
		return gallery;
	}
	
	// 컨트롤러 메서드들에서 예외가 발생했을 때의 처리
	@ExceptionHandler(FileUploadException.class)
	// public ModelAndView handle(FileUploadException e) {
	public String handle(FileUploadException e) {
		// ModelAndView mav = new ModelAndView("error/result");
		// mav.addObject("e", e);
		
		// return mav;
		return "error";
	}
	@ExceptionHandler(GalleryException.class)
	// public ModelAndView handle(GalleryException e) {
	public String handle(GalleryException e) {
		// ModelAndView mav = new ModelAndView("error/result");
		// mav.addObject("e", e);
		
		// return mav;
		return "error";
	}
	@ExceptionHandler(PhotoException.class)
	// public ModelAndView handle(PhotoException e) {
	public String handel(PhotoException e) {
		// ModelAndView mav = new ModelAndView("error/result");
		// mav.addObject("e", e);
		
		// return mav;
		return "error";
	}
}
