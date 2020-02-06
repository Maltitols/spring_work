package com.gura.spring05.cafe.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.cafe.dto.CafeDto;

public interface CafeService {
	public void list(HttpServletRequest request);
	public void getDetail(ModelAndView mView, HttpServletRequest request, int num);
	public void addViewCount(int num);
	public void insert(HttpServletRequest request, CafeDto dto);
}
