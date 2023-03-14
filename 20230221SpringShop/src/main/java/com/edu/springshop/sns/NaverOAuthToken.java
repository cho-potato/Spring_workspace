package com.edu.springshop.sns;

import lombok.Data;

// naver 서버에서 전송 받은 JSON 문자열을 자바객체로 담아놓기 위한 목적
@Data
public class NaverOAuthToken {

	private String access_token; // 직접적으로 중요한 토큰 : 회원정보를 갖고 있음
	private String refresh_token;
	private String token_type;
	private int expires_in; //유효기간

}
