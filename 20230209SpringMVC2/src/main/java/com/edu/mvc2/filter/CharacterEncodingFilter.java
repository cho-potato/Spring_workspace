package com.edu.mvc2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*
 * 필터 인터페이스는 서블릿이 요청을 받기도 전에 요청을 낚아내는 메서드를 지원해준다
 * 따라서 이 필터 인터페이스를 재정의하여 우리만의 기능을 필터로 구현해본다
 * 계획 : 파라미터에 대한 인코딩 처리 후 서블릿에게 제어권도 넘겨줘야 함
 * 필터를 쓰게 되면 요청이 끊어지기 때문에 가던 길 가게 만들어줘야 한다 (chain에 기존 서블릿 명시) 
 */
public class CharacterEncodingFilter implements Filter {
	String encoding;
	// 필터 객체가 생성될 때 초기화 작업 사용
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter("encoding");
	}
	// 요청이 들어올 때 동작, 즉 서블릿보다 먼저 요청을 낚아챈다
	// 낚아챈 이후에는 서블릿으로 요청이 흘러가게끔 진행해줘야한다
	// 서블릿은 자신이 받은 요청 이전에 어떤 필터가 동작했는지 알 수 없다
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// request.setCharacterEncoding("UTF-8"); // 파라미터에 대한 한글처리
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response); // 가던 길 가게만들기
	}

	// 필터가 소멸될 때 
	public void destroy() {

	}

}
