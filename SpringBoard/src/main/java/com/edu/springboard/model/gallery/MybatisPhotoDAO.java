package com.edu.springboard.model.gallery;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.edu.springboard.domain.Photo;
import com.edu.springboard.exception.PhotoException;

@Repository
public class MybatisPhotoDAO implements PhotoDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List selectAll() {
		return sqlSessionTemplate.selectList("Photo.selectAll");
	}

	public Photo select(int photo_idx) {
		return sqlSessionTemplate.selectOne("Photo.select", photo_idx);
	}

	public void insert(Photo photo) throws PhotoException {
		int result=sqlSessionTemplate.insert("Photo.insert", photo);
		if(result<1) {
			throw new PhotoException("photo 입력 실패");
		}
	}

	public void deleteByGallery(int gallery_idx) throws PhotoException {
		int result=sqlSessionTemplate.delete("Photo.delete", gallery_idx);
		if(result<1) {
			throw new PhotoException("photo 삭제 실패");
		}
	}

}
