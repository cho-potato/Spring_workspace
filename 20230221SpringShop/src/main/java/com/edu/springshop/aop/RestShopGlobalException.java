package com.edu.springshop.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.edu.springshop.exception.EmailException;
import com.edu.springshop.exception.HashException;
import com.edu.springshop.exception.MemberException;
import com.edu.springshop.util.Message;

// 하위 컨트롤러에서 해당 예외가 발생했을 때 해당 하위 컨트롤러에서 지정된 @ExceptionHandler가 먼저 적용되고,
// 만일 해당 @ExceptionHandler가 지정되지 않으면 이 예외 객체가 예외를 처리한다
// 일종의 Exception에 대한 예외조치
// @ControllerAdvice
// @RestControllerAdvice 자체가 @RestControllerAdvice에서 발생하는 예외만 처리한다는 보장을 하지 않는다
// @RestControllerAdvice // @ControllerAdvice + ResponseBody
@RestControllerAdvice(annotations = RestController.class)
public class RestShopGlobalException {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(HashException.class)
	public ResponseEntity<Message> handle(HashException e) {
		
		Message message = new Message();
		message.setMsg("RestShopGlobalException, HashException : 회원가입 실패"+ e.getMessage());
		
		ResponseEntity entity = new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}
	
	@ExceptionHandler(EmailException.class)
	public ResponseEntity<Message> handle(EmailException e) {
		
		Message message = new Message();
		message.setMsg("RestShopGlobalException, EmailException : 회원가입 실패"+ e.getMessage());
		
		ResponseEntity entity = new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}

	@ExceptionHandler(MemberException.class)
	public ResponseEntity<Message> handle(MemberException e) {
		
		Message message = new Message();
		message.setMsg("RestShopGlobalException, MemberException : 회원가입 실패"+ e.getMessage());

		ResponseEntity entity = new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}
}
