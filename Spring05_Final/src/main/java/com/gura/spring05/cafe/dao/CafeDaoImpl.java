package com.gura.spring05.cafe.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring05.cafe.dto.CafeDto;

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
		return session.selectOne("cafe.getData2", num);
	}
	
	@Override
	public CafeDto getData(CafeDto dto) {
		return session.selectOne("cafe.getData", dto);
	}

	@Override
	public void addViewCount(int num) {
		session.update("cafe.addViewCount", num);	
	}
	
	@Override
	public void insert(CafeDto dto) {
		session.insert("cafe.insert", dto);
	}
	
	@Override
	public void delete(int num) {
		session.delete("cafe.delete", num);
	}
	
	@Override
	public void update(CafeDto dto) {
		session.update("cafe.update", dto);
	}
}
