package com.edu.springshop.model.admin;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.springshop.domain.Admin;
import com.edu.springshop.exception.AdminException;

@Repository
public class MybatisAdminDAO implements AdminDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void insert(Admin admin) throws AdminException{
		int result = sqlSessionTemplate.insert("Admin.insert", admin);
		
		if(result < 1) {
			throw new AdminException("관리자 등록 실패");
		}
	}

	public Admin select(Admin admin) throws AdminException {
		Admin obj = sqlSessionTemplate.selectOne("Admin.select", admin);
		
		if(obj==null) { // select한 정보가 없으면 Exception을 던져야지
			throw new AdminException("올바르지 않은 정보입니다");
		}
		return obj;
	}
}
