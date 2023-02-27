package com.edu.springshop.model.member;

import java.util.List;

import com.edu.springshop.domain.Member;

public interface MemberService {
	public List selectAll();
	public Member select(Member member);
	public void regist(Member member);
	public void update(Member member);
	public void unregist(Member member);
}
