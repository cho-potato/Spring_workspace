package com.edu.springshop.sns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

// 인증화면과 관련된 URL 및 파라미터, 토큰을 얻기 위한 URL과 파라미터를 보관할 클래스
// 필수는 아니지만, URL과 파라미터가 상당히 복잡하고 길기 때문에 이 클래스를 정의해놓으면
// 링크를 쉽게 관리할 수 있다(by zino)
@Data
public class KakaoLogin {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 아래 데이터는 서블릿이 메모리에 올라올 때 우루루 올라올 예정
	private String endpoint;
	private String client_id;
	private String redirect_uri;
	private String response_type;
	
	// 토큰을 위한 변수
	private String token_request_url;
	private String grant_type;
	
	// 사용자 정보 조회를 위한 변수
	private String userinfo_url;
	
	// 스프링 설정 파일에서 넘겨 받은 정보들을 이용하여 인증화면 링크 만들자
	public String getGrantUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append(endpoint+"?client_id="+client_id);
		sb.append("&redirect_uri="+redirect_uri);
		sb.append("&response_type="+response_type);
		
		return sb.toString();
	}
}
