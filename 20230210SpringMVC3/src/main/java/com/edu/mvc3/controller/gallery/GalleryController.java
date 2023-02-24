package com.edu.mvc3.controller.gallery;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.edu.mvc3.domain.Gallery;
import com.edu.mvc3.exception.UploadException;
import com.edu.mvc3.model.GalleryService;
import com.edu.mvc3.model.util.FileManager;

import lombok.Setter;

// 갤러리와 관련된 모든 요청을 처리할 수 있는 하위 컨트롤러
// POJO 추구 : 개발자에게 구현을 강제하지 않는다
// 더이상 XML에 등록하지 않으므로, 컨트롤러 클래스에 무언가 표시를 해야 스프링이 인스턴스를 생성해ㅜ준다
// 특히 스프링이 생성할 인스턴스의 대상이 되는 객체들을 컴포넌트라 하는데
// 이 컴포넌트의 대표적인 종류로는 @Conroller @Service @Repository @Component
@Controller 
@Setter
public class GalleryController {
	// URL 매핑도 xml이 아닌 자바 클래스 안에 둔다
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	// 스프링의 빈 컨테이너가 보유한 빈들 중 아래의 멤버변수명이나 자료형과 일치하는 빈이 있을 경우
	// Auotowired가 자동으로 Setter를 주입해준다
	@Autowired
	private FileManager fileManager;
	private GalleryService galleryService;
	// 글의 목록요청 처리
	// @RequestMapping(value="URL주소");
	@RequestMapping(value="/client/gallery/list")
	public String getList() {
		logger.info("GalleryController : 갤러리 목록 요청 받음");
		
		// 비록 string을 반환하지만, 
		return "gallery/list"; // URI가 아니라 자원의 위치, 
	}
	
	// 갤러리 등록 폼 요청 처리
	@RequestMapping(value="/client/gallery/registform")
	public String getRegistForm() {
		return "gallery/regist";
	}
	
	// 갤러리 등록 요청
	@RequestMapping(value="/client/gallery/regist")
	public String regist(Gallery gallery, HttpServletRequest request) {
		 logger.info("getTitle" + gallery.getTitle());
		 logger.info("getWriter" + gallery.getWriter());
		 logger.info("getContent" + gallery.getContent());
		
		MultipartFile file = gallery.getFile();
		String filename = file.getOriginalFilename();
		// logger.info("파일명은 "+filename);
		
		// 현재 업로드 된 파일은 물리적 파일로 존재하지 않고 메모리상에 존재하므로
		// 서버의 로컬 디렉토리에 저장을 해봐 
		
		ServletContext servletContext = request.getSession().getServletContext(); // 컨트롤러의 역할
		
		// 경로가 지정되어 있으면 안되므로 기존 코드 주석처리하고 뺴냄
		// String realPath = servletContext.getRealPath("/resources/data/"); // 컨트롤러의 역할  but 경로가 안에 있으면 안됨 -> MyListener로 대체
		// logger.info("데이터 실제 경로는" + realPath);
		String realPath = (String)servletContext.getAttribute("dataPath");
		String finalName=fileManager.createFileName(filename);
		
		fileManager.save(gallery, realPath+finalName);
		
		// 현재 날짜 얻어오기
				// long time = System.currentTimeMillis();
				// String ext = filename.substring(filename.lastIndexOf("."), filename.length());
				
				// String finalName = time+"."+ext;

		/*
		try { // 물리적 저장
			// file.transferTo("목적 디렉토리");
			file.transferTo(new File(realPath+finalName)); 
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		// 외부에서 바라보는 URL 경로
		return "redirect:/client/gallery/list"; // 클라이언트로 하여금 글 등록하고 재접속을 유도한다
	}
	// 현재 이 컨트롤러의 모든 요청처리 메서드에서 에러, 즉 예외가 발생하면
	// 아래의 ExceptionHandler가 동작하게 되어 있는데 해다 예외클래스에 맞는 메서드만 호출됨
	@ExceptionHandler(UploadException.class)
	public ModelAndView handle(UploadException e) { // UploadException가 클래스 원본이기에 올려준다
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/error/result");
		mav.addObject("e", e); // 에러 객체 저장하기
		
		// 개발자가 redirect 명시하지 않았으므로 error.jsp로 forwarding 한다
		
		return mav;
	}
}
