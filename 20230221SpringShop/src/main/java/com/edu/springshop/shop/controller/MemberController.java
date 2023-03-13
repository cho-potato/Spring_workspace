package com.edu.springshop.shop.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Member;
import com.edu.springshop.model.member.MemberService;
import com.edu.springshop.sns.GoogleLogin;
import com.edu.springshop.sns.GoogleOAuthToken;
import com.edu.springshop.sns.KakaoLogin;
import com.edu.springshop.sns.KakaoOAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpMethods;

//회원관 관련된 요청을 처리하는 하위 컨트롤러 
@Controller
public class MemberController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MemberService memberService;

	@Autowired
	private GoogleLogin googleLogin;
	
	@Autowired
	private KakaoLogin kakaoLogin;

	// 회원가입 폼 요청처리
	@GetMapping("/member/joinform")
	public ModelAndView getJoinForm(HttpServletRequest request) {

		return new ModelAndView("shop/member/joinform");
	}

	// 로그인 폼 요청처리
	@GetMapping("/member/loginform")
	public ModelAndView getLoginForm(HttpServletRequest request) {

		return new ModelAndView("shop/member/loginform");
	}

	// 회원가입 요청 처리
	// HttpServletRequest를 넣어야 하는이유? AOP적용을 위한 CategoryAdvice 코드에
	// 요청을 낚아채어, request 를 사용중이므로...
	@PostMapping("/member/regist")
	public ModelAndView regist(HttpServletRequest request, Member member) {
		// 3단계:
		memberService.regist(member);
		ModelAndView mav = new ModelAndView("redirect:/member/loginform");
		return mav;
	}

	// 상담게시판 페이지 요청 처리
	@GetMapping("/member/chatform")
	public ModelAndView getChatForm(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("shop/member/chat");
		return mav;
	}
	/*
	 * @GetMapping("/member/authform/google") public ModelAndView
	 * getAuthForm(HttpServletRequest request) {
	 * 
	 * String url = googleLogin.handle();
	 * 
	 * ModelAndView mav = new ModelAndView("shop/member/loginform");
	 * mav.addObject("url", url);
	 * 
	 * return mav; }
	 */

	// 구글 로그인 콜백
	@GetMapping("/sns/google/callback")
	public ModelAndView googleCallback(HttpServletRequest request, HttpSession session) {
		String code = request.getParameter("code");
		logger.info("callback : 구글에서 발급된 코드는, " + code);

		// 구글이 넘겨준 code와 내 계정이 보유한 client_id 및 client_secret을 조합하여
		// 구글 서버 측에 token 발급을 요청해야 한다 => (POST) head+body를 구성하여 요청 시도
		// 이 때, 우리 스프링 서버는 상대적으로 클라이언트가 된다
		// token의 용도 : 회원정보에 접근할 수 있는 증명서 개념 ...

		/*------------------------------------------------------------------
		 1) 토큰 취득을 위한 POST header + Body 구성하기
		 -------------------------------------------------------------------*/
		String url = googleLogin.getToken_request_url();

		// (몸을 먼저 만들고 머리를 만드는 순서임)
		// body의 파라미터 구성하기 <파라미터명, 파라미터값>
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", googleLogin.getClient_id());
		params.add("client_secret", googleLogin.getClient_secret());
		params.add("redirect_uri", googleLogin.getRedirect_uri());		
		params.add("grant_type", googleLogin.getGrant_type());

		// POST 방식의 header(application/x-www-form-urlencoded)
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");

		// 머리와 몸 합치기
		HttpEntity httpEntity = new HttpEntity(params, headers);

		// 요청시도를 위한 객체생성, 비동기 방식의 요청을 위한 객체
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		// logger.info("callback : 구글에서 넘겨 받은 응답정보는, " + responseEntity);

		/*------------------------------------------------------------------
		 2) 토큰 요청 후 ResponseEntity로부터 토큰 꺼내기 (그냥 String에 불과하므로)
		 -------------------------------------------------------------------*/
		String body = responseEntity.getBody(); // 몸을 끄집어내기
		logger.info("callback : 구글에서 넘겨 받은 응답정보는, " + body);

		// JSON으로 되어 있는 문자열을 파싱하여 자바의 객체로 옮겨담자
		ObjectMapper objectMapper = new ObjectMapper();
		// 위 매퍼를 이용하여 해석
		GoogleOAuthToken oAuthToken = null;

		try {
			oAuthToken = objectMapper.readValue(body, GoogleOAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// oAuthToken 안에 있는 토큰을 이용하여 회원 정보에 접근해보자
		/*------------------------------------------------------------------
		 3) 토큰을 이용하여 회원정보 접근
		 -------------------------------------------------------------------*/
		String userinfo_url = googleLogin.getUserinfo_url();

		// GET 방식 요청 구성
		HttpHeaders headers2 = new HttpHeaders();
//		headers2.add("Authorization", "Bearer (한칸 띄고)"+토큰);
		headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
//		HttpEntity entity = new HttpEntity(매개 변수에 헤더 값);
		HttpEntity entity = new HttpEntity(headers2);

		// 비동기 객체를 이용한 GET 요청
		RestTemplate restTemplate2 = new RestTemplate();
		ResponseEntity<String> userEntity = restTemplate2.exchange(userinfo_url, HttpMethod.GET, entity, String.class);

		// 머리는 필요없고 몸체만 필요함
		String userBody = userEntity.getBody();
		logger.info(userBody);
		HashMap<String, Object> userMap = new HashMap<String, Object>();

		// 사용자 정보 추출하기 (jackson converter 이용)
		ObjectMapper objectMapper2 = new ObjectMapper();
		try {
			userMap = objectMapper2.readValue(userBody, HashMap.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String id = (String) userMap.get("id");
		String email = (String) userMap.get("email");
		boolean verified_email = (Boolean) userMap.get("verified_email");
		String nickname = (String) userMap.get("nickname");

		logger.info("id" + id);
		logger.info("email" + email);
		logger.info("verified_email" + verified_email);
		logger.info("nickname" + nickname);

		/*
		 * if(false) { // 이미 DB에 이 회원의 식별 고유 id가 존재할 경우 // 회원가입을 처리해주자 (서비스의 regist, 세션에
		 * 담자) } else { // 그렇지 않은 경우 // 로그인 처리만 하자 (세션에 담자) }
		 */
		// 메인으로 보내자
		// ModelAndView mav = new ModelAndView(redirecrt:/)

		// 대신 해주는 객체가 있는데 Jackson Converter
		// 5개의 변수를 담아 놓을 객체가 필요하다 GoogleOAuthToken

		// return mav;
		return null;

	}

	// 카카오 로그인 콜백
	@GetMapping("/sns/kakao/callback")
	public ModelAndView kakaoCallback(HttpServletRequest request, HttpSession session) {
		String code = request.getParameter("code");
		logger.info("callback : 카카오에서 발급된 코드는, "+ code);
		
		// 구글이 넘겨준 code와 내 계정이 보유한 client_id 및  client_secret을 조합하여
		// 구글 서버 측에 token 발급을 요청해야 한다 => (POST) head+body를 구성하여 요청 시도
		// 이 때, 우리 스프링 서버는 상대적으로 클라이언트가 된다 
		// token의 용도 : 회원정보에 접근할 수 있는 증명서 개념 ...

		/*------------------------------------------------------------------
		 1) 토큰 취득을 위한 POST header + Body 구성하기
		 -------------------------------------------------------------------*/
		 
		String url=kakaoLogin.getToken_request_url();
		
		// (몸을 먼저 만들고 머리를 만드는 순서임)
		// body의 파라미터 구성하기 <파라미터명, 파라미터값>
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", kakaoLogin.getClient_id());
		params.add("redirect_uri", kakaoLogin.getRedirect_uri());
		params.add("grant_type", kakaoLogin.getGrant_type());
		
		// POST 방식의 header(application/x-www-form-urlencoded)
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		
		// 머리와 몸 합치기
		HttpEntity httpEntity = new HttpEntity(params, headers);
		
		// 요청시도를 위한 객체생성, 비동기 방식의 요청을 위한 객체
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(url,  HttpMethod.POST, httpEntity, String.class);
		// logger.info("callback : 구글에서 넘겨 받은 응답정보는, " + responseEntity);

		/*------------------------------------------------------------------
		 2) 토큰 요청 후 ResponseEntity로부터 토큰 꺼내기 (그냥 String에 불과하므로)
		 -------------------------------------------------------------------*/
		String body = responseEntity.getBody(); // 몸을 끄집어내기
		logger.info("callback : 카카오에서 넘겨 받은 응답정보는, " + body);
		
		// JSON으로 되어 있는 문자열을 파싱하여 자바의 객체로 옮겨담자
		ObjectMapper objectMapper = new ObjectMapper();
		// 위 매퍼를 이용하여 해석
		KakaoOAuthToken oAuthToken=null;
		
		try {
			oAuthToken = objectMapper.readValue(body, KakaoOAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// oAuthToken 안에 있는 토큰을 이용하여 회원 정보에 접근해보자
		/*------------------------------------------------------------------
		 3) 토큰을 이용하여 회원정보 접근
		 -------------------------------------------------------------------*/
		String userinfo_url = kakaoLogin.getUserinfo_url();
		
		// GET 방식 요청 구성
		HttpHeaders headers2 = new HttpHeaders();
//		headers2.add("Authorization", "Bearer (한칸 띄고)"+토큰);
		headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
//		HttpEntity entity = new HttpEntity(매개 변수에 헤더 값);
		HttpEntity entity = new HttpEntity(headers2);
		
		// 비동기 객체를 이용한 GET 요청
		RestTemplate restTemplate2 = new RestTemplate();
		ResponseEntity<String> userEntity = restTemplate2.exchange(userinfo_url, HttpMethod.GET, entity, String.class);
		
		// 머리는 필요없고 몸체만 필요함
		String userBody = userEntity.getBody(); 
		logger.info(userBody);
		HashMap<String, Object> userMap = new HashMap<String, Object>();
		
		// 사용자 정보 추출하기 (jackson converter 이용)
		ObjectMapper objectMapper2 = new ObjectMapper();
		try {
			userMap = objectMapper2.readValue(userBody, HashMap.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		/*
		String id = (String)userMap.get("id");
		String email = (String)userMap.get("email");
		boolean verified_email = (Boolean)userMap.get("verified_email");
		String nickname = (String)userMap.get("nickname");

		logger.info("id"+id);
		logger.info("email"+email);
		logger.info("verified_email"+verified_email);
		logger.info("nickname"+nickname);
		*/
		/*
		if(false) {
			// 이미 DB에 이 회원의 식별 고유 id가 존재할 경우
			// 회원가입을 처리해주자 (서비스의 regist, 세션에 담자)
		} else {
			// 그렇지 않은 경우
			// 로그인 처리만 하자 (세션에 담자)
		}
		 */
		// 메인으로 보내자
		// ModelAndView mav = new ModelAndView(redirecrt:/)
		
		// 대신 해주는 객체가 있는데 Jackson Converter
		// 5개의 변수를 담아 놓을 객체가 필요하다 GoogleOAuthToken
		
		
		// return mav;
		return null;
		
	}
}
