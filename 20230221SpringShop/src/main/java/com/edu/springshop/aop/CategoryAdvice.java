package com.edu.springshop.aop;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.model.category.CategoryService;

/*
 * 쇼핑몰 어플리케이션에 전반적으로 적용될 수 있는 공통 코드를 AOP기술의
 * Advice로 정리해놓고 필요한 곳에 적용시켜보자
 */
public class CategoryAdvice {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CategoryService categoryService; 
	
	// 아래 메서드에서 서비스의 selectAll()을 호출하여 ModelAndView에 저장
	// public void getCategoryList(ProceedingJoinPoint joinPoint) {
	public Object getCategoryList(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object returnObj = null; // return값이기 때문에 맨 위로 올림
		
		// joinPoint의 목적 : 원래 호출하려던 메서드를 호출 전, 후에 관여할 수 있는 기능을 지원
		String target = joinPoint.getTarget().getClass().getName();
		// getTarget : 원래 호출하려던 객체
		logger.info("원래 호출하려던 객체는 target is " + target);
		
		Signature sig = joinPoint.getSignature();
		String method = sig.getName();
		logger.info("원래 호출하려던 메서드는 target is " + method);
		
		// 호출하려던 메서드의 매개변수에서 request 객체 가져오기
		Object[] args = joinPoint.getArgs();
		HttpServletRequest request = null; // 보관 
		for(Object arg : args) {
			if(arg instanceof HttpServletRequest) {
				request= (HttpServletRequest)arg;
			}
		}
		
		String uri = request.getRequestURI();
		
		if( // 제외될 요청 URI = 카테고리 처리 필요 없는 요청들
			uri.equals("/rest/member") || // 비동기 방식의 가입요청은 메뉴 제외
			uri.equals("/member/regist") // 동기방식의 가입요청은 메뉴 제외
				) {
			returnObj = joinPoint.proceed();
		} else {
			
			// 원래는 컨트롤러들에서 매 번 수행해야 했던 Category 가져오기
			// 공통 코드를 여기서 수행해버리자
			List categoryList = categoryService.selectAll();
			
			// 원래 호출하려던 메서드를 진행시킨다 (joinPoint가 모든 것을 알고 있음)
			
			ModelAndView mav = null;
			// joinPoint.proceed();
			returnObj = joinPoint.proceed(); // 원래 호출하려던 메서드 호출하기 위해
			// returnObj는 getList메서드의 ModelAndView이기 때문에 new 해줄 필요 없다
				
			if(returnObj instanceof ModelAndView) { // returnObj의 자료형이 ModelAndView라면 ,,,
				mav = (ModelAndView)returnObj;
				mav.addObject("categoryList", categoryList);
				returnObj=mav; // 어차피 returnObj를 반환하니까 반환값을 mav로 대입
				}
		}
		return returnObj;
	} 
}
