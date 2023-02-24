package com.edu.springboard.model.gallery;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.springboard.domain.Gallery;
import com.edu.springboard.exception.GalleryException;

@Repository
public class MybatisGalleryDAO implements GalleryDAO{
	
	// Mybatis-Spring에서는 기존 Mybati의 쿼리 담당 객체였던 SqlSession이
	// SqlSessionTemplate로 변경
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List selectAll() {
		return sqlSessionTemplate.selectList("Gallery.selectAll");
	}
	public Gallery select(int gallery_idx) {
		return sqlSessionTemplate.selectOne("Gallery.select", gallery_idx);
	}
	
	public void insert(Gallery gallery) throws GalleryException {
		int result = sqlSessionTemplate.insert("Gallery.insert", gallery);
		if(result < 1) {
			throw new GalleryException("등록실패");
		}
	}
	public void update(Gallery gallery) throws GalleryException {
		int result = sqlSessionTemplate.update("Gallery.update", gallery);
		if(result < 1) {
			throw new GalleryException("수정실패");
		}
	}
	public void delete(int gallery_idx) throws GalleryException {
		int result = sqlSessionTemplate.delete("Gallery.delete", gallery_idx);
		if(result < 1) {
			throw new GalleryException("삭제실패");
		}
	}
}
