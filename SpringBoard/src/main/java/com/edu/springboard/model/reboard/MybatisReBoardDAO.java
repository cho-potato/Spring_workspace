package com.edu.springboard.model.reboard;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.springboard.domain.ReBoard;
import com.edu.springboard.exception.ReBoardException;

@Repository
public class MybatisReBoardDAO implements ReBoardDAO{
	
	/*
	 @Autowired의 역할 :
	 xml로 표현했다면 아래와 같이 했었어야 했다.
	 <bean class="~~MybatisReBoardDAO">
	 	<property name="sqlSessionTeamplate" ref="sqlSessionTemplate">
	 </bean>	 
	 */
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List selectAll() {
		return sqlSessionTemplate.selectList("ReBoard.selectAll");
	}

	public ReBoard select(int reboard_idx) {
		return sqlSessionTemplate.selectOne("ReBoard.select", reboard_idx);
	}

	public void insert(ReBoard reboard) throws ReBoardException{
		int result=sqlSessionTemplate.insert("ReBoard.insert", reboard);
		if(result<1) {
			throw new ReBoardException("원글 등록 실패");
		}
	}

	public void update(ReBoard reboard) throws ReBoardException{
		int result=sqlSessionTemplate.update("ReBoard.update", reboard);
		if(result<1) {
			throw new ReBoardException("글 수정 실패");
		}
	}

	public void delete(int reboard_idx) throws ReBoardException{
		int result=sqlSessionTemplate.delete("ReBoard.delete", reboard_idx);
		if(result<1) {
			throw new ReBoardException("글 삭제 실패");
		}
	}

	public void updateStep(ReBoard reboard) {//추후 수정 예정
		int result=sqlSessionTemplate.update("ReBoard.updatestep", reboard);
	}

	public void reply(ReBoard reboard) throws ReBoardException {//추후 수정 예정
		int result=sqlSessionTemplate.insert("ReBoard.reply", reboard);
		if(result<1) {
			throw new ReBoardException("답글 등록 실패");
		}
	}

}
