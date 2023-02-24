package com.edu.hibernateapp.hibernate.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.edu.hibernateapp.hibernate.HibernateConfig;
import com.edu.hibernateapp.hibernate.domain.Notice;

public class NoticeDAO {
	HibernateConfig config = HibernateConfig.getInstance(); // 쿼리문 수행하려고
	
	// public NoticeDAO() {
		// HibernateConfig config = HibernateConfig.getInstance(); 멤버로 뺴기
	//}
	public void insert() {
		// 팩토리 얻기
		SessionFactory factory = config.getSessionFactory();
		Session session=factory.openSession();
		
		// 트랜잭션 시작
		Transaction tx = session.beginTransaction();
		
		// Hibernate는 쿼리문 작성에 의한 업무가 아닌 DTO제어를 통해 테이블과의 CRUD 수행
		Notice notice = new Notice();
		notice.setTitle("하이버네이트 수업");
		notice.setWriter("하이버네이트작성자");
		notice.setContent("하이버네이트 내용");
		
		// DTO를 Hibernate 한테 먹인 적이 없으므로 먹이자
		session.save(notice); // insert 동작
		
		tx.commit();
		config.relaese(factory);
	}
}
