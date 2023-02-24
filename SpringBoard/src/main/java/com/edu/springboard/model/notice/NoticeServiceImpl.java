package com.edu.springboard.model.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.edu.springboard.domain.Notice;
import com.edu.springboard.exception.NoticeException;

@Service
public class NoticeServiceImpl implements NoticeService{
	@Autowired
	private NoticeDAO noticeDAO;
	
	public List selectAll() {
		return noticeDAO.selectAll();
	}

	public Notice select(int notice_idx) {
		return noticeDAO.select(notice_idx);
	}

	public void insert(Notice notice) throws NoticeException{
		noticeDAO.insert(notice);
	}

	public void update(Notice notice)throws NoticeException {
		noticeDAO.update(notice);
		
	}

	public void delete(int notice_idx)throws NoticeException {
		noticeDAO.delete(notice_idx);
	}

}
