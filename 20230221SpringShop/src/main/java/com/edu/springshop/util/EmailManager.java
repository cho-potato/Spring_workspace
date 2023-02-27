package com.edu.springshop.util;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import com.edu.springshop.domain.Member;
import com.edu.springshop.exception.EmailException;

public class EmailManager {

	@Autowired
	private JavaMailSender javaMailSender;
	
	// 메일 보내기
	public void send(Member member) throws EmailException {
		// 이메일 정보를 담을 메세지 객체를 생성
		MimeMessage msg = javaMailSender.createMimeMessage();
		
		try {
			// 받을 사람 정보 설정
			msg.addRecipient(RecipientType.TO, new InternetAddress(member.getEmail()));
			
			// 보내는  사람 정보 설정
			msg.addFrom(new InternetAddress[] {
					new InternetAddress("ymcho.dev@gmail.com", "webmaster")
					// 보내는 사람이 여러 명이면 new InternetAddress() 여러 개
					// 앞자리에는 보내는 사람의 주소, 
			});
			
			// 메일 제목
			msg.setSubject("회원가입 완료");
			
			// 메일 내용
			msg.setText("회원가입을 축하드립니다", "UTF-8");
			
			// 메일 전송
			javaMailSender.send(msg);
			
		} catch (AddressException e) {
			e.printStackTrace();
			throw new EmailException("받는 사람 정보 설정 실패", e);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new EmailException("받는 사람 정보 설정 실패", e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new EmailException("보내는 사람 정보 설정 실패", e);
		}
	}
}
