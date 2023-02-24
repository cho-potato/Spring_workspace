package com.edu.mv2.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextLorderListener implements ServletContextListener{
	ServletContext context;//jsp에서의 application 내장객체
									//application.getRealPath()에 이용했었다.,
									//javaee 기반의 서버의 메모리에서 데이터를 개발자가 심을 수 있는 객체가 3가지
									//request<session<application
	ApplicationContext applicationContext;
	
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("서버 시작 감지");
		sce.getServletContext().getInitParameter("root-context.xml");
		//web.xml 에서 context의 parameter
		
		ServletContext context=sce.getServletContext();
		applicationContext=new ClassPathXmlApplicationContext("root-context.xml");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("서버 중지 감지");
		
	}

}
