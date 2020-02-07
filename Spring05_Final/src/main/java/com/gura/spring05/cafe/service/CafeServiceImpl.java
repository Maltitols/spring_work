package com.gura.spring05.cafe.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.cafe.dao.CafeDao;
import com.gura.spring05.cafe.dto.CafeDto;
import com.gura.spring05.exception.CannotDeleteException;
import com.gura.spring05.file.dto.FileDto;

@Service
public class CafeServiceImpl implements CafeService{
	@Autowired
	private CafeDao dao;

	@Override
	public void list(HttpServletRequest request) {
		/*
		 * request에 검색 keyword가 전달될수도 있고 안될수도 있다.
		 * 전달 안되는경우 : navbar에서 파일 눌러서 바로 목록보기로 가는경우
		 * 전달 되는경우 : 검색조건 설정해서 키워드넣고 검색한경우
		 * 전달 되는 다른경우 : 이미 검색을 한 상태에서 하단 페이지번호를 누른경우
		 */
		//검색과 관련된 파라미터를 읽어와 본다.
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");
		
		//검색 키워드가 존재한다면 키워드를 담을 FileDto 객체 생성 
		CafeDto dto=new CafeDto();
		if(keyword != null) {//검색 키워드가 전달된 경우
			if(condition.equals("titlecontent")) {//제목+내용 검색
				dto.setTitle(keyword);
				dto.setContent(keyword);
			}else if(condition.equals("title")) {//제목 검색
				dto.setTitle(keyword);
			}else if(condition.equals("writer")) {//작성자 검색
				dto.setWriter(keyword);
			}
			/*
			 *  검색 키워드에는 한글이 포함될 가능성이 있기 때문에
			 *  링크에 그대로 출력가능하도록 하기 위해 미리 인코딩을 해서
			 *  request 에 담아준다.
			 */
			String encodedKeyword=null;
			try {
				encodedKeyword=URLEncoder.encode(keyword, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//키워드와 검색조건을 request 에 담는다. 
			request.setAttribute("keyword", keyword);
			request.setAttribute("encodedKeyword", encodedKeyword);
			request.setAttribute("condition", condition);
		}			
		
		//한 페이지에 나타낼 row 의 갯수
		final int PAGE_ROW_COUNT=5;
		//하단 디스플레이 페이지 갯수
		final int PAGE_DISPLAY_COUNT=3;
		
		//보여줄 페이지의 번호
		int pageNum=1;
		//보여줄 페이지의 번호가 파라미터로 전달되는지 읽어와 본다.	
		String strPageNum=request.getParameter("pageNum");
		if(strPageNum != null){//페이지 번호가 파라미터로 넘어온다면
			//페이지 번호를 설정한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		//전체 row 의 갯수를 읽어온다.
		int totalRow=dao.getCount(dto);
		//전체 페이지의 갯수 구하기
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//시작 페이지 번호
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못된 값이라면 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //보정해준다. 
		}		
		// 위에서 계산된 startRowNum 과 endRowNum 을 dto에 담는다.
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);

		//1.DB에서 파일목록을 얻어온다 
		List<CafeDto> list=dao.getList(dto);
		//2. view page에 필요한 값을 request에 담아둔다.
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("list", list);
		//전체 글의 개수도 담기
		request.setAttribute("totalRow", totalRow);
	}
	@Override
	public void getDetail(ModelAndView mView, HttpServletRequest request, int num) {
		CafeDto dto=dao.getData(num);
		mView.addObject("dto", dto);
		request.setAttribute("num", dto.getNum());
		request.setAttribute("writer", dto.getWriter());
		request.setAttribute("title", dto.getTitle());
		request.setAttribute("regdate", dto.getRegdate());
		request.setAttribute("content", dto.getContent());
	}
	@Override
	public void addViewCount(int num) {
		dao.addViewCount(num);
	}
	@Override
	public void insert(HttpServletRequest request, CafeDto dto) {
		String id=(String)request.getSession().getAttribute("id");
		dto.setWriter(id);
		dao.insert(dto);
	}
	@Override
	public void delete(HttpServletRequest request) {
		int num=Integer.parseInt(request.getParameter("num"));
		//파일 작성자와 로그인된 아이디가 다르면 예외를 발생
		CafeDto dto=dao.getData(num);
		String id=(String)request.getSession().getAttribute("id");
		if(!id.equals(dto.getWriter())) {
			//예외를 발생시켜서 메소드가 정상수행되지 않도록 막는다
			throw new CannotDeleteException();
		}
		dao.delete(num);
	}
	@Override
	public void update(CafeDto dto) {
		dao.update(dto);
	}
}
