package com.edu.springshop.aop;

/*
 이 클래스는 어플리케이션에서 공통 로직으로 정의 
 
 어플리케이션의 핵심 관심사항(Core Concern)은 아니지만 
 모든 영역에 전반적으로 사용되는 기능을 공통 관심사항 (Cross Cutting Concern) 이라 한다
 Bell을 공통관심사항으로 정리헤보자
 
 공통관심사항 코드 : Advice 라고 표현 동시에, 시점을 Advice라고도 한다
 */

public class Bell {
	public void ding() {
		System.out.println("ringdingdong");
	}
}
