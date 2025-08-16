package com.shop.home.enums;

public enum Categories {
	FOODS ("Foods"),
	VEGETABLES ("Vegetables"),
	ELECTRONICS ("Electronics"),
	CLOTHES ("Clothes"),
	SPORTS ("Sports"),
	BOOKS ("Books");
	
	private String module;
	Categories(String module){
		this.module=module;
	}
}
