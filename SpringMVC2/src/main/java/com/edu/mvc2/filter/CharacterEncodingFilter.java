package com.edu.mvc2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*
 필터 인터페이스는, 서블릿이 요청을 받기도 전에 요청을 낚아채는 메서드를 지원해준다.
 따라서 이 필터 인터페이스를 재정의하여, 우리만의 기능을 필터로 재정의하여, 우리만의 기능을 
 필터로 재구현해본다.
 
 계획) 파라미터에 대한 인코딩 처리 후, 서블릿에 제어권 넘겨주기
 
 * */
public class CharacterEncodingFilter implements Filter{
	String encoding;
	//필터객체가 생성될 때 초기화 작업 사용
	public void init(FilterConfig filterConfig) throws ServletException {
		//서블릿과 비슷
		encoding=filterConfig.getInitParameter("encoding");
		/*
		 web.xml에서 가져오기
		 <init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
		 */
	}

	//요청을 들어올때 호출됨.. 즉 서블릿 보다 먼저 요청을 낚아챈다
	//따라서 낚아 챈 이후엔 다시 서블릿으로 요청이 들어가게끔 진행해줘야 한다.
	//서블릿은 자신이 받은 요청 이전에 어떤 필터가 동작했는지 모른다.
	//계곡물 filter-윗물, servlet - 아랫물
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//request.setCharacterEncoding("utf-8"); 직접 작성X
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);//가던길 그대로 가게 하기
		//이걸 막으면 jsp도 안먹힘 why? jsp도 서블릿이기 때문에, 필터는 서블릿 이전에 동작
		
	}
	
	//필터가 소멸될 때..
	public void destroy() {
		
	}

}
