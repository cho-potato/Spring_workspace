package com.edu.springboard.model.gallery;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.edu.springboard.domain.Gallery;
import com.edu.springboard.domain.Photo;
import com.edu.springboard.exception.FileUploadException;
import com.edu.springboard.exception.GalleryException;
import com.edu.springboard.exception.PhotoException;
import com.edu.springboard.model.util.FileManager;

@Service
public class GalleryServiceImpl implements GalleryService{
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	//@Qualifier("mybatisGalleryDAO")
	@Qualifier("jdbcGalleryDAO")
	private GalleryDAO galleryDAO;
	
	@Autowired
	//@Qualifier("mybatisPhotoDAO")
	@Qualifier("jdbcPhotoDAO")
	private PhotoDAO photoDAO;
	
	@Autowired
	private FileManager fileManager;

	public List selectAll() {
		
		return galleryDAO.selectAll();
	}

	public Gallery select(int gallery_idx) {
		return galleryDAO.select(gallery_idx);
	}

	public void regist(Gallery gallery, String path) throws FileUploadException, GalleryException, PhotoException{
		//fileManager 시키기
		fileManager.save(gallery, path);
		
		//galleryDAO 
		//mybatis의 selectkey에 의해 insert문 수행 이후에는 Gallery DTO
		//의 gallery_idx  값이 채워져 있을 거임
		logger.info("insert 이전의 Gallery의 gallery_idx값"+gallery.getGallery_idx());
		galleryDAO.insert(gallery);
		logger.info("insert 이후의 Gallery의 gallery_idx값"+gallery.getGallery_idx());
		
		//photoDAO시키기 : gallery-mutilpart에 filename이 들어있음
		//필요한 모든건 Gallery에 들어있다.
		
		//FileManager에 의해 채워진 photoList 활용하기
		List<Photo> photoList=gallery.getPhotoList();
		
		for(int i=0;i<photoList.size();i++) {
			Photo photo=photoList.get(i);
			photo.setGallery(gallery);//fk를 위하여 넣어주기
			photoDAO.insert(photo);
		}
		
	}

	public void update(Gallery gallery) {
		
	}

	public void delete(Gallery gallery) {
		
	}



}
