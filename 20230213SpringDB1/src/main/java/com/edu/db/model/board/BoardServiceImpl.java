package com.edu.db.model.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edu.db.domain.Board;
import com.edu.db.exception.BoardException;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	@Qualifier("mybatisBoardDAO") // @Qualifier("아이디")
	private BoardDAO boardDAO; // 

	public List selectAll() {
		return boardDAO.selectAll();
	}
	public Board select(int board_idx) {
		return boardDAO.select(board_idx);
	}
	public void insert(Board board) throws BoardException {
		boardDAO.insert(board); // 컨트롤러까지 오류가 전달되어야 하므로
	}
	public void update(Board board) throws BoardException {
		boardDAO.update(board); // 컨트롤러까지 오류가 전달되어야 하므로
		
	}
	public void delete(int board_idx) throws BoardException {
		boardDAO.delete(board_idx); // 컨트롤러까지 오류가 전달되어야 하므로
	}
}
