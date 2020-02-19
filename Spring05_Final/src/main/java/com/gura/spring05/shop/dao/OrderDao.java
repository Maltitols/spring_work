package com.gura.spring05.shop.dao;

import com.gura.spring05.shop.dto.OrderDto;

public interface OrderDao {
	//배송 정보 추가
	public void addOrder(OrderDto dto);
}
