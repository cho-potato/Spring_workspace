package com.edu.mvc2.model.board;

import java.util.List;

import com.edu.mvc2.domain.Board;

/*
 * 곧 이 객체를 Service가 사용할 예정이므로,
 * 서비스 입장에서는 DAO에 대한 결합도가 낮아야 한다
 * 따라서, BoardDAO의 최상위 객체를 정의한다
 * 
 */
public interface BoardDAO {
	// 뭘 쓰든 아래의 CRUD 계획에는 변함없다
	public List selectAll();
	public Board select(int board_idx);
	public void insert(Board board);
	public void update(Board board);
	public void delete(int board_idx);

}
