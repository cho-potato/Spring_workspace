package com.edu.springshop.sns;

import lombok.Data;

// google 서버에서 전송 받은 JSON문자열을 자바객체로 담아놓기 위한 목적
@Data
public class GoogleOAuthToken {

	private String access_token; // 직접적으로 중요한 토큰 : 회원정보를 갖고 있음
	private int expires_in; //유효기간 
	private String scope; 
	private String token_type;
	private String id_token;

}
