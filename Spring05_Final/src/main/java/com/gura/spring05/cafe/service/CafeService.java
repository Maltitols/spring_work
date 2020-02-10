package com.gura.spring05.cafe.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.cafe.dto.CafeCommentDto;
import com.gura.spring05.cafe.dto.CafeDto;

public interface CafeService {
	public void list(HttpServletRequest request);
	public void getDetail(HttpServletRequest request);
	public void insert(CafeDto dto);
	public void delete(int num, HttpServletRequest request);      
	public void getUpdateData(ModelAndView mView, int num);
	public void update(CafeDto dto);
	//댓글 저장하는 메소드
	public void saveComment(HttpServletRequest request);
	//댓글 삭제하는 메소드
	public void deleteComment(int num);
	//댓글 수정하는 메소드
	public void updateComment(CafeCommentDto dto);
}
