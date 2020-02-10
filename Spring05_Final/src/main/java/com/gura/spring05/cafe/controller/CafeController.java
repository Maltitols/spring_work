package com.gura.spring05.cafe.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.cafe.dto.CafeCommentDto;
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
	public String detail(HttpServletRequest request) {
		service.getDetail(request);
		return "cafe/detail";
	}
	
	@RequestMapping("/cafe/insertform")
	public ModelAndView authInsertForm(HttpServletRequest request) {
		
		return new ModelAndView("cafe/insertform");
	} 
	
	@RequestMapping(value="/cafe/insert", method=RequestMethod.POST)
	public ModelAndView authInsert(HttpServletRequest request, @ModelAttribute CafeDto dto) {
		//세션에 있는 글작성자의 아이디
		String writer=(String)
				request.getSession().getAttribute("id");
		//CafeDto 객체에 담고 
		dto.setWriter(writer);
		//서비스를 이용해서 DB 에 저장
		service.insert(dto);
		//글 목록 보기로 리다일렉트 이동 
		return new ModelAndView("redirect:/cafe/list.do");
	}
	
	@RequestMapping("/cafe/delete")
	public ModelAndView authDelete(HttpServletRequest request, @RequestParam int num) {
		service.delete(num, request);
		return new ModelAndView("redirect:/cafe/list.do");
	}
	
	@RequestMapping("/cafe/updateform")
	public ModelAndView authUpdateForm(ModelAndView mView, HttpServletRequest request, @RequestParam int num) {
		//서비스를 이용해서 수정할 글정보를 ModelAndView
		//객체에 담고
		service.getUpdateData(mView, num);
		//view page 로 forward 이동해서 수정폼 출력
		mView.setViewName("cafe/updateform");
		return mView;
	}
	
	@RequestMapping(value="/cafe/update", method=RequestMethod.POST)
	public ModelAndView authUpdate(HttpServletRequest request, @ModelAttribute CafeDto dto) {
		service.update(dto);
		return new ModelAndView
				("redirect:/cafe/detail.do?num="+dto.getNum());
	}
	//댓글 저장 요청 처리
	@RequestMapping(value = "/cafe/comment_insert", 
			method = RequestMethod.POST)
	public ModelAndView authCommentInsert(HttpServletRequest request,
			@RequestParam int ref_group) {
		service.saveComment(request);
		return new ModelAndView("redirect:/cafe/detail.do?num="+ref_group);
	}
	
	//댓글 삭제 요청 처리
	@ResponseBody
	@RequestMapping(value = "/cafe/comment_delete", 
			method = RequestMethod.POST)
	public Map<String, Object> 
		authCommentDelete(HttpServletRequest request,
			@RequestParam int num){
		
		service.deleteComment(num);
		
		Map<String, Object> map=new HashMap<>();
		map.put("isSuccess", true);
		return map; // {"isSuccess":true} 형식의 JSON 문자열이 응답된다.
	}
	//댓글 수정 요청 처리(ajax)
	@ResponseBody
	@RequestMapping("/cafe/comment_update")
	public Map<String, Object> 
		authCommentUpdate(HttpServletRequest request,
				@ModelAttribute CafeCommentDto dto){
		
		service.updateComment(dto);
		
		Map<String, Object> map=new HashMap<>();
		map.put("isSuccess", true);
			return map;
		}
}
