package gui.school;

public class Student {
	// 원래는 SchoolBell이지만 
	// Bell bell; // 의존성 약화시키기 위한 상위 객체 이용
	/*
	// 생성자 주입도 주입으로 인정되다
	public Student(Bell bell) {
		this.bell=bell;
	}
	*/
	// 학생의 일과
	public void goSchool() {
		// bell.ring();
		System.out.println("등교합니다");
	}
	public void startStudy() {
		// bell.ring();
		System.out.println("수업 시작합니다");
	}
	public void endStudy() {
		// bell.ring();
		System.out.println("수업 종료합니다");
	}
	public void haveLunch() {
		// bell.ring();
		System.out.println("점심식사합니다");
	}
	public void startStudy2() {
		// bell.ring();
		System.out.println("오후 수업 시작합니다");
	}
	public void goHome() {
		// bell.ring();
		System.out.println("하교합니다");
	}
}
