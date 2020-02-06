package com.gura.spring05.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/*
 * Exception이 발생했을때 처리하는 컨트롤러
 * 
 * -@ControllerAdvice 어노테이션을 붙인다
 * 
 * -메소드에 @ExceptionHandler(예외 class type)을 붙여서 예외처리
 */
@ControllerAdvice
public class ExceptionController {
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(CannotDeleteException.class)
	public ModelAndView forbidden() {
		ModelAndView mView=new ModelAndView();
		mView.addObject("msg", "남의파일 건들지마라");
		mView.setViewName("error/forbidden");
		return mView;
	}
}
