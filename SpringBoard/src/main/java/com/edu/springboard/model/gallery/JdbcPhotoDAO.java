package com.edu.springboard.model.gallery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.edu.springboard.domain.Photo;
import com.edu.springboard.exception.PhotoException;

@Repository
public class JdbcPhotoDAO implements PhotoDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List selectAll() {
		String sql="select * from photo order by photo_idx desc";
		List photoList=jdbcTemplate.query(sql, new RowMapper<Photo>() {

			public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
				Photo photo=new Photo();
				photo.setPhoto_idx(rs.getInt("photo_idx"));
				photo.setFilename(rs.getNString("filename"));
				return photo;
			}
		});
		return photoList;
	}

	public Photo select(int photo_idx) {
		String sql="select * from photo where photo_idx=?";
		
		Photo photo=jdbcTemplate.queryForObject(sql, new RowMapper<Photo>() {

			public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
				Photo photo=new Photo();
				photo.setPhoto_idx(rs.getInt("photo_idx"));
				photo.setFilename(rs.getNString("filename"));
				return photo;
			}
		});
		return photo;
	}

	public void insert(Photo photo) throws PhotoException {
		StringBuilder sb=new StringBuilder();
		sb.append("insert into photo(photo_idx, gallery_idx, filename)");
		sb.append(" values(seq_photo.nextval, ?, ?)");
		
		int result=jdbcTemplate.update(sb.toString(), new Object[] {photo.getGallery().getGallery_idx(), photo.getFilename()});
		
		if(result<1) {
			throw new PhotoException("Photo 입력 실패");
		}
	}

	@Override
	public void deleteByGallery(int gallery_idx) throws PhotoException{
		StringBuilder sb=new StringBuilder();
		sb.append("delete from photo");
		sb.append("where gallery_idx=?");
		
		int result=jdbcTemplate.update(sb.toString(), new Object[] {gallery_idx});
		
		if(result<1) {
			throw new PhotoException("Photo 삭제 실패");
		}
	}

}
