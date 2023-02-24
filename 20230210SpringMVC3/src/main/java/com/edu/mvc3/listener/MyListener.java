package com.edu.mvc3.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		// 풀경로가 아닌 루트를 기준으로 한 경로 
		String dataPath = application.getInitParameter("dataPath");
		String path = application.getRealPath(dataPath);
		
		application.setAttribute("dataPath", path);
	}
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
