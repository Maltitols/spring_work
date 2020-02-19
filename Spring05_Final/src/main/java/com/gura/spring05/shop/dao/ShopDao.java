package com.gura.spring05.shop.dao;

import java.util.List;

import com.gura.spring05.shop.dto.ShopDto;

public interface ShopDao {
	//목록리턴
	public List<ShopDto> getList();
	//재고 -
	public void minusCount(int num);
	//잔고 -
	public void minusMoney(ShopDto dto);
	//포인트 +
	public void plusPoint(ShopDto dto);
	//제품 가격 리턴
	public int getPrice(int num);
}
