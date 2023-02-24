package com.edu.springboard.model.notice;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.edu.springboard.domain.Notice;
import com.edu.springboard.exception.NoticeException;

@Repository
public class MybatisNoticeDAO implements NoticeDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List selectAll() {
		return sqlSessionTemplate.selectList("Notice.selectAll");
	}

	public Notice select(int notice_idx) {
		return sqlSessionTemplate.selectOne("Notice.select", notice_idx);
	}

	public void insert(Notice notice) throws NoticeException{
		int result=sqlSessionTemplate.insert("Notice.insert", notice);
		if(result<1) {
			throw new NoticeException("입력실패");
		}
	}

	public void update(Notice notice) throws NoticeException{
		int result=sqlSessionTemplate.update("Notice.update", notice);
		if(result<1) {
			throw new NoticeException("수정실패");
		}
	}

	public void delete(int notice_idx) throws NoticeException{
		int result=sqlSessionTemplate.delete("Notice.delete", notice_idx);
		if(result<1) {
			throw new NoticeException("삭제실패");
		}
	}

}
