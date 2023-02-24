package com.edu.springboard.android;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {
	ServerSocket server; // 대화용이 아닌 접속감지용 서버소켓
	Thread thread; // 실행부를 루프나 대기상태로 빠지지 않게 하기 위한 쓰레드 생성
	
	// 접속자 명단
	// ArrayList 좋은 방법이긴  하지만 쓰레드간 충돌을 원천 방지하기 위해서는 Vector()를 사용하자
	Vector<ChatThread> clientList = new Vector<ChatThread>();
	
	public ChatServer() {
		thread = new Thread() {

			public void run() {
				
				try {
					server = new ServerSocket(8000); //기다리는 쪽이기 때문에 소켓만 있어도 됨
					System.out.println("서버 소켓 생성");
					while(true) { // 다수의 접속자 무한 감지
					Socket socket = server.accept();
					System.out.println("접속자 발견");
					
					// 서버 측의 대화 당사자 탄생, 소켓 넘겨줘야 함
					ChatThread chatThread = new ChatThread(socket, ChatServer.this);
					
					// 접속자 명단에 추가 // 명단이 있어야 멀티캐스팅 가능
					clientList.add(chatThread);
					System.out.println("(ChatServer)현재 접속자는 : "+ clientList.size());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	
	public static void main(String[] args) {
		new ChatServer();
	}
}
