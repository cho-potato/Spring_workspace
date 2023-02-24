package com.edu.springboard.model.gallery;

import java.util.List;

import org.apache.commons.fileupload.FileUploadException;

import com.edu.springboard.domain.Gallery;

public interface GalleryService {
	public List selectAll(); // DAO동일
	public Gallery select(int gallery_idx); // DAO 동일
	public void regist(Gallery gallery, String path); // insert()+upload();를 대신할 수 있는 
	public void update(Gallery gallery); // DB뿐만 아니라 이미지까지 같이 하는 것 DAO와는 다르다
	public void delete(Gallery gallery); // DB + file
}
