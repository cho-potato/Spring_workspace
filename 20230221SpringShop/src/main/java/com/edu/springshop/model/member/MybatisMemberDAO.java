package com.edu.springshop.model.member;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.springshop.domain.Member;
import com.edu.springshop.exception.MemberException;

@Repository
public class MybatisMemberDAO implements MemberDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	public List selectAll() {
		return sqlSessionTemplate.selectList("Member.selectAll");
	}

	public Member select(Member member) {
		return sqlSessionTemplate.selectOne("Member.select", member);
	}

	public void insert(Member member) throws MemberException {
		int result = sqlSessionTemplate.insert("Member.insert", member);
		result=0;
		if(result < 1) {
			throw new MemberException("Member insert : 등록실패");
		}
	}

	public void update(Member member) throws MemberException {
		int result = sqlSessionTemplate.update("Member.update", member);
		if(result < 1) {
			throw new MemberException("Member update : 수정실패");
		}
	}

	public void delete(Member member) throws MemberException {
		int result = sqlSessionTemplate.delete("Member.delete", member);
		if(result < 1) {
			throw new MemberException("Member delete : 삭제실패");
		}
	}
}
