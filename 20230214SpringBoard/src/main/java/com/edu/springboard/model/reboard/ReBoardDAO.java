package com.edu.springboard.model.reboard;

import java.util.List;

import com.edu.springboard.domain.ReBoard;

public interface ReBoardDAO {
	public List selectAll();
	public ReBoard select(int reboard_idx);
	public void insert(ReBoard reboard);
	public void update(ReBoard reboard);
	public void delete(int reboard_idx);
	
	// 답변 관련
	public void updateStep(ReBoard reboard); // 자리 확보
	public void reply(ReBoard reboard); // 답변 등록
}
