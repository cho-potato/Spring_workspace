package com.edu.hibernateapp.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
	private static HibernateConfig instance;
	private SessionFactory sessionFactory;
	// String resource="설정파일 위치"
	String resource="com/edu/hibernateapp/hibernate/hibernate.cfg.xml";
		
	private HibernateConfig() {
		// 설정 XML을 읽기
		Configuration config = new Configuration().configure(resource);
		StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
		// sb.applySettings("Map들어가야함");
		sb.applySettings(config.getProperties()); // 위에 있는것과 연결됨 //xml설정을 서비스 레지스트리가 인식
		
		StandardServiceRegistry registry = sb.build(); // 여기까지 해야 SessionFactory 올릴 수 있음
		
		sessionFactory = config.buildSessionFactory(registry);
	}
	
	public static HibernateConfig getInstance() {
		if(instance==null) {
			instance = new HibernateConfig();
		}
		return instance;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void relaese(SessionFactory sessionFactory) {
		sessionFactory.close(); // 반납
	} 
	
}
