package com.edu.mvc2.model.board;



import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.edu.mvc2.domain.Board;
import com.edu.mvc2.exception.BoardException;
import com.edu.mvc2.mybatis.MybatisConfig;

import lombok.Setter;

@Setter
public class BoardServiceImpl implements BoardService{
	//DI주입받자
	private MybatisConfig config=MybatisConfig.getInstance();//싱글턴도 주입 가능
	private BoardDAO boardDAO;

	public List selectAll() {
		SqlSession sqlSession=config.getSqlSession();
		MybatisBoardDAO dao=(MybatisBoardDAO)boardDAO;
		dao.setSqlSession(sqlSession);
		List list=dao.selectAll();
		
		config.release(sqlSession);
		return list;
	}

	public Board select(int board_idx) {
		SqlSession sqlSession=config.getSqlSession();
		MybatisBoardDAO dao=(MybatisBoardDAO)boardDAO;
		dao.setSqlSession(sqlSession);
		
		Board board=dao.select(board_idx);
		
		config.release(sqlSession);

		return board;
	}

	public void insert(Board board) {
		SqlSession sqlSession=config.getSqlSession();
		MybatisBoardDAO dao=(MybatisBoardDAO)boardDAO;
		dao.setSqlSession(sqlSession);
		
		try {
			dao.insert(board);
			sqlSession.commit();
		} catch (BoardException e) {
			sqlSession.rollback();
			e.printStackTrace();
		}finally {
			config.release(sqlSession);
		}
	}

	public void update(Board board) {
		SqlSession sqlSession=config.getSqlSession();
		MybatisBoardDAO dao=(MybatisBoardDAO)boardDAO;
		dao.setSqlSession(sqlSession);
		
		try {
			dao.update(board);
			sqlSession.commit();
		} catch (BoardException e) {
			sqlSession.rollback();
			e.printStackTrace();
		}finally {
			config.release(sqlSession);	
		}
	}

	public void delete(int board_idx) {
		SqlSession sqlSession=config.getSqlSession();
		MybatisBoardDAO dao=(MybatisBoardDAO)boardDAO;
		dao.setSqlSession(sqlSession);
		
		try {
			dao.delete(board_idx);
			sqlSession.commit();
		} catch (BoardException e) {
			sqlSession.rollback();
			e.printStackTrace();
		}finally {
			config.release(sqlSession);			
		}
	}

}
