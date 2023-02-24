package com.edu.db.model.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.db.domain.Board;
import com.edu.db.exception.BoardException;

// 아래의 어노테이션을 무조건 붙이는 게 아니라, bean 설정 파일의 양을 줄이고자,\
// xml에 빈등록을 하지 않았을 때 사용하는 방법
// 즉, component-scan을 이용하고자 할 때 사용하는 것
@Repository
public class MybatisBoardDAO implements BoardDAO {
	@Autowired	
	private SqlSessionTemplate sqlSessionTemplate;

	public List selectAll() {
		return sqlSessionTemplate.selectList("Board.selectAll");
	}
	public Board select(int board_idx) {
		return sqlSessionTemplate.selectOne("Board.select", board_idx);
	}
	public void insert(Board board) throws BoardException {
		int result = sqlSessionTemplate.insert("Board.insert", board);
		if(result < 1) {
			throw new BoardException("등록실패");
		}
	}
	public void update(Board board) throws BoardException {
		int result = sqlSessionTemplate.update("Board.update", board);
		if(result < 1) {
			throw new BoardException("수정실패");
		}
	}
	public void delete(int board_idx) {
		int result = sqlSessionTemplate.delete("Board.delete", board_idx);
		if(result < 1) {
			throw new BoardException("삭제실패");
		}
	}
}
