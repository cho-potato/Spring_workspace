package com.edu.springmvc1.model.emp;

import java.util.List;

import com.edu.springmvc1.domain.Emp;

// 컨트롤러가 사용할 이 객체는 DI를 적용하기 위해, 즉 컨트롤러와의 의존성을 약화시키기 위함
// 어버이는 선언의 성격만 있기 때문에 어노테이션은 자식한테 붙일거임
public interface EmpService {
	public void regist(Emp emp);
	public List selectAll();
	public Emp select(int empno);
	public void remove(Emp emp);
}
