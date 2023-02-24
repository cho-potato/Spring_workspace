package com.edu.db.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener{
/*
 * ApplicationContext (스프링 컨테이너)
 * ServletContext (어플리케이션 내장객체의 자료형 )
 * ServletConfig
 	- (Service init()의 매개변수로서 초기화 파라미터인 init-param을 가지고 있거나 ServletContext를 얻어올 때 사용되는 환경정보 객체)
 */

	public void contextInitialized(ServletContextEvent sce) {
		// 어플리케이션 내장객체 얻기
		ServletContext application =sce.getServletContext();
		String savePath = sce.getServletContext().getInitParameter("savePath");
		savePath = application.getRealPath(savePath); // 경로 덮어쓰자
		// application.setAttribute("savePath", "/resources/data");
		application.setAttribute("savePath", savePath);
	}

	public void contextDestroyed(ServletContextEvent sce) {

		
	}
}
