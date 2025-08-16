package com.shop.home.enums;

public enum Stocks {
	AVAILABLE ("Available"),
	OUT_OF_STOCKS ("Out Of Stocks");
	
	private String stock;
	Stocks(String s){
		this.stock=s;
	}
}
