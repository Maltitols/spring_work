package com.gura.spring05.shop.dto;

public class ShopDto {
	private int num;
	private String name;
	private int price;
	private int remainCount;
	private String id;
	
	public ShopDto() {}

	public ShopDto(int num, String name, int price, int remainCount, String id) {
		super();
		this.num = num;
		this.name = name;
		this.price = price;
		this.remainCount = remainCount;
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
