package test.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PenAspect {
	/*
	 * spring이 관리하는 객체의 메소드 중에서
	 * 리턴type은 void, 메소드명은 write1
	 * 메소드에 전달되는 인자는 없는 메소드의 
	 * 수행 이전에 할 작업
	 */
	@Before("execution(void write*())")
	public void prepare() {
		System.out.println("Pen을 준비해요!");
	}
	@After("execution(void write*())")
	public void end() {
		System.out.println("Pen을 마무리해요!");
	}
}
