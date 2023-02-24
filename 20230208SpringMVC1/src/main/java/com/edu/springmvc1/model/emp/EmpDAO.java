package com.edu.springmvc1.model.emp;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.edu.springmvc1.domain.Emp;
import com.edu.springmvc1.exception.EmpException;

@Repository
public class EmpDAO {
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
		
	public void insert(Emp emp) throws EmpException {
		int result = sqlSession.insert("Emp.insert", emp);
		if(result < 1) {
			throw new EmpException("사원등록 실패");
		}
	}
	public List selectAll() {
		return sqlSession.selectList("Emp.selectAll");
	}
	public Emp select(int empno) {
		return sqlSession.selectOne("Emp.select", empno);
	}
	
	public void delete(int empno) throws EmpException {
		int result = sqlSession.delete("Emp.delete", empno);
		if(result < 1) {
			throw new EmpException("사원 삭제 실패");
		}
	}
}