package com.edu.db.model.board;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.edu.db.domain.Board;

@Repository
public class JdbcBoardDAO implements BoardDAO {

	public List selectAll() {
		return null;
	}
	public Board select(int board_idx) {
		return null;
	}
	public void insert(Board board) {
		
	}
	public void update(Board board) {
		
	}
	public void delete(int board_idx) {

	}
}
