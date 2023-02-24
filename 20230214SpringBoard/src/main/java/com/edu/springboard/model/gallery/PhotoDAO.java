package com.edu.springboard.model.gallery;

import java.util.List;

import com.edu.springboard.domain.Photo;

public interface PhotoDAO {
	public List selectAll();
	public Photo select(int photo_idx);
	// selectByGallery는 mybatis가 내부적으로 호출하는 것이기 때문에 필요 없음
	public void insert(Photo photo);
	public void deleteByGallery(int photo_idx);
	
}
