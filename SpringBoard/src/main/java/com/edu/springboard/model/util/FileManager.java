package com.edu.springboard.model.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.edu.springboard.domain.Gallery;
import com.edu.springboard.domain.Photo;
import com.edu.springboard.exception.FileUploadException;

@Component
public class FileManager {

	//확장자 구하기
	public String getExt(String path) {
		int lastIndex=path.lastIndexOf(".");//가장 마지막 점의 index
		String ext=path.substring(lastIndex+1, path.length());
		
		return ext;
	}
	
	//파일명 얻기
	public String createFileName(String path) {
		long time=System.currentTimeMillis();
		String filename=time+"."+getExt(path);
		
		return filename;
	}
	
	//서버에 저장
	//서버의 하드디스크 풀 경로를 두번째 인수로 넘겨야 한다.(realPath 넘겨야함)
	public void save(Gallery gallery, String path) throws FileUploadException {
		MultipartFile[] photo=gallery.getPhoto();//업로드한 파일 정보
		//해결책 : gallery DTO의 비어있는 photoList를 여기서 채우자
		List<Photo> photoList=new ArrayList<Photo>();
		gallery.setPhotoList(photoList);
		
		
		try {
			for(int i=0;i<photo.length;i++) {
				//변수로 뺀 이유 : List 채우고 밑에서 쓰기 위해
				
				try {
					Thread.sleep(10);//createFileName에서 속도가 너무 빨라서 중복되는 것을 방지함
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String filename=createFileName(photo[i].getOriginalFilename());
				Photo dto=new Photo();
				dto.setFilename(filename);
				
				photoList.add(dto);//만든 파일명을 넣어줌
				photo[i].transferTo(new File(path+filename));
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new FileUploadException("파일 저장 실패", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileUploadException("파일 저장 실패", e);
		}
		
		
	}
	
	
	
	
}
