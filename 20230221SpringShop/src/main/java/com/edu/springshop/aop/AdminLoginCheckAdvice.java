package com.edu.springshop.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.springshop.exception.AdminException;

public class AdminLoginCheckAdvice {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// public void sessionCheck(ProceedingJoinPoint joinPoint) throws AdminException, Throwable {
	public Object sessionCheck(ProceedingJoinPoint joinPoint) throws AdminException, Throwable {
		Object result = null; // proceed() 후 반환되는 객체를 담기위한 변수(ModelANdView,,,String,,,뭐가 될지 알 수 없음)
		
		// ProceedingJoinPoint : 주로 타겟에 대한 정보를 가지고 있다
		// 원래 호출하려던 타겟 객체가 무엇인지, 그 타겟 객체의 무슨 메서드를 호출하려 했는지, 그 타켓 메서드의 매개변수는 무엇인지
		
		Class targetClass = joinPoint.getTarget().getClass(); // 원래 호출하려던 동생 컨르롤러 반환
		
		Object[] args = joinPoint.getArgs(); // 매개변수는 배열로 반환함
		
		logger.info("sessionCheck : 호출하려는 타겟 클래스는, " +  targetClass.getName());
		logger.info("sessionCheck : 호출하려는 타겟 메서드의 매개변수 수는, " + args.length);
		
		// 현재 이 요청에 대해 session에 값이 들어있는지 여부를 조사해보자
		// 원래 호출하려 했던 메서드의 매개변수 정보를 현재 대리객체가 추출할 수 있으므로
		// 원래 호출하려 했던 메서드에는 HttpServletRequest가 명시되어 있어야 한다
		// 타겟 메서드에서 HttpServletRequest 추출하여 session에 관리자 객체가 들어있는지 판단
		// 체크하기
		HttpServletRequest request = null;
		HttpSession session = null;
		
		for(int i = 0; i<args.length; i++) {
			if(args[i] instanceof HttpServletRequest) { // 몇 번째가 요청객체인지 모르니 instanceof 로 알아보자
				request = (HttpServletRequest)args[i];
			}
		}
		// 로그인을 체크해야 하는 경우, 그냥 보내야 하는 경우를 나눈다
		String uri = request.getRequestURI(); // /admin/main 같은 URI
		if(
				uri.equals("/admin/loginform") || // 로그인 폼 요청시 제외
				uri.equals("/admin/rest/login/admin") || // 비동기 로그인 요청시 제외
				uri.equals("/admin/login") // 동기방식으로 로그인 요청이 들어올 때 제외				
				) {
			result = joinPoint.proceed(); // 여기서 proceed는 주로 동생 컨트롤러들의 메서드 = 반환형을 받아준다
		} else {
			// 로그인이 필요한 서비스에서만 다음의 코드들이 수행되어야 한다
			session = request.getSession(); // 세션 가져오기
			
			if(session.getAttribute("admin")==null) {
				logger.info("sessionCheck : aop에 의한 로그인 체크, 세션 없음");
				throw new AdminException("로그인이 필요한 서비스입니다"); 
			} else {
				result = joinPoint.proceed();// 로그인 한 사람은 가던 길 가게끔
			}
		}
		return result;
	}
}
