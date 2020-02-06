package com.gura.spring05.cafe.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring05.cafe.dto.CafeDto;
import com.gura.spring05.file.dto.FileDto;

@Repository
public class CafeDaoImpl implements CafeDao{
	@Autowired
	private SqlSession session;

	@Override
	public List<CafeDto> getList(CafeDto dto) {
		List<CafeDto> list=session.selectList("cafe.getList",dto);
		return list;
	}

	@Override
	public int getCount(CafeDto dto) {
		//검색조건에 맞는 파일의 전체 개수를 select해서
		int count=session.selectOne("cafe.getCount", dto);
		return count;
	}

	@Override
	public CafeDto getData(int num) {
		CafeDto dto=session.selectOne("cafe.getData", num);
		return dto;
	}

	@Override
	public void addViewCount(int num) {
		session.update("cafe.addViewCount", num);	
	}
	
	@Override
	public void insert(CafeDto dto) {
		session.insert("cafe.insert", dto);
	}
}
