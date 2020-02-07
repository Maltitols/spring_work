package com.gura.spring05.cafe.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.cafe.dto.CafeDto;
import com.gura.spring05.cafe.service.CafeService;

@Controller
public class CafeController {
	@Autowired CafeService service;
	
	@RequestMapping("/cafe/list")
	public ModelAndView list(ModelAndView mView, HttpServletRequest request) {
		//파일 목록과 페이징처리에 필요한 값들을 request에 담아주는 서비스 메소드 호출
		service.list(request);
		
		mView.setViewName("cafe/list");
		return mView;
	}
	
	@RequestMapping("/cafe/detail")
	public ModelAndView detail(ModelAndView mView, HttpServletRequest request, @RequestParam int num) {
		service.getDetail(mView, request, num);
		service.addViewCount(num);
		mView.setViewName("cafe/detail");
		return mView;
	}
	
	@RequestMapping("/cafe/insertform")
	public ModelAndView authInsertForm(ModelAndView mView, HttpServletRequest request) {
		
		return new ModelAndView("cafe/insertform");
	} 
	
	@RequestMapping(value="/cafe/insert", method=RequestMethod.POST)
	public ModelAndView authInsert(HttpServletRequest request, @ModelAttribute CafeDto dto) {
		service.insert(request, dto);
		return new ModelAndView("redirect:/cafe/list.do");
	}
	
	@RequestMapping("/cafe/delete")
	public ModelAndView authDelete(HttpServletRequest request) {
		service.delete(request);
		return new ModelAndView("redirect:/cafe/list.do");
	}
	
	@RequestMapping("/cafe/updateform")
	public ModelAndView authUpdateForm(ModelAndView mView, HttpServletRequest request, @RequestParam int num) {
		service.getDetail(mView, request, num);
		mView.setViewName("cafe/updateform");
		return mView;
	}
	
	@RequestMapping(value="/cafe/update", method=RequestMethod.POST)
	public ModelAndView authUpdate(@ModelAttribute CafeDto dto) {
		service.update(dto);
		return new ModelAndView("redirect:/cafe/list.do");
	}
}
