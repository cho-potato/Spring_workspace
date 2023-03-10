package com.edu.mvc2.model.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.edu.mvc2.domain.Board;
import com.edu.mvc2.exception.BoardException;

import lombok.Setter;

@Setter
public class MybatisBoardDAO implements BoardDAO{
	// MybatisConfig config = MybatisConfig.getInstance(); 서비스로 올림
	private SqlSession sqlSession;
	
	public List selectAll() {
		return sqlSession.selectList("Board.selectAll");
	}

	public Board select(int board_idx) {
		return sqlSession.selectOne("Board.select", board_idx);
	}

	public void insert(Board board) throws BoardException{
		int result = sqlSession.insert("Board.insert", board);
		if(result <1) {
			throw new BoardException("등록실패");
		}
	}

	public void update(Board board) {
		int result = sqlSession.update("Board.update", board);
		if(result <1) {
			throw new BoardException("수정실패");
		}
	}

	public void delete(int board_idx) {
		int result = sqlSession.delete("Board.delete", board_idx);
		if(result <1) {
			throw new BoardException("삭제실패");
		}
	}
}
