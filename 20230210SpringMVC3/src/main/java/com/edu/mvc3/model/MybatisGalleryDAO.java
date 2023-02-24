package com.edu.mvc3.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.edu.mvc3.domain.Gallery;
import com.edu.mvc3.exception.GalleryException;

import lombok.Setter;

// component-scan에 의해 검색되어 자동으로 인스턴스 생성됨
@Repository
@Setter
public class MybatisGalleryDAO implements GalleryDAO {
	// SqlSession은 스프링에 등록한 적이 없기 때문에 주입은 GalleryService가 주도한다
	// 스프링이 지원하는 DB연동기술을 배우면 이 문제는 해결된다
	private SqlSession sqlSession;
	
	public List selectAll() {
		return sqlSession.selectList("Gallery.selectAll");
	}

	public Gallery select(int gallery_idx) {
		return sqlSession.selectOne("Gallery.select", gallery_idx);
	}

	public void insert(Gallery gallery) {
		int result = sqlSession.insert("Gallery.insert", gallery);
		if(result <1) {
			throw new GalleryException("등록 실패");
		}
	}

	public void update(Gallery gallery) {
		int result = sqlSession.update("Gallery.update", gallery);
		if(result <1) {
			throw new GalleryException("수정 실패");
		}
	}

	public void delete(int gallery_idx) {
		int result = sqlSession.delete("Gallery.delete", gallery_idx);
		if(result <1) {
			throw new GalleryException("삭제 실패");
		}
	}


}
