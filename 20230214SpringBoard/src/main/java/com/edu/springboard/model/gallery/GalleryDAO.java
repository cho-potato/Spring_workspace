package com.edu.springboard.model.gallery;

import java.util.List;

import com.edu.springboard.domain.Gallery;

// interface는 new 할 수 없다 = 어노테이션(@) ㄴㄴ
public interface GalleryDAO {
	public List selectAll();
	public Gallery select(int gallery_idx);
	public void insert(Gallery gallery);
	public void update(Gallery gallery);
	public void delete(int gallery_idx);

}
