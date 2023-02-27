package com.edu.springshop.model.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.edu.springshop.domain.Member;
import com.edu.springshop.exception.EmailException;
import com.edu.springshop.exception.HashException;
import com.edu.springshop.exception.MemberException;
import com.edu.springshop.util.EmailManager;
import com.edu.springshop.util.PassConverter;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private PassConverter passConverter;
	
	@Autowired
	private EmailManager emailManager;
	
	@Autowired
	private MemberDAO memberDAO;
	
	public List selectAll() {
		return memberDAO.selectAll();
	}

	public Member select(Member member) {
		return memberDAO.select(member);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void regist(Member member) throws HashException, EmailException, MemberException {
		// 암호화
		String hash = passConverter.convertHash(member.getPass());
		member.setPass(hash); // DTO의 패스워드를 hash 값으로 대체
		
		// 이메일
		// emailManager.send(member);
		
		// DB insert
		memberDAO.insert(member);
		
	}

	public void update(Member member) {
	
	}


	public void unregist(Member member) {
		
	}
}
