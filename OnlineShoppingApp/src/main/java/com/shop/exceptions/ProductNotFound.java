package com.shop.exceptions;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class ProductNotFound extends Exception {

	private String msg;
	public ProductNotFound(String msg) {
		super();
		this.msg = msg;
	}
	public ProductNotFound() {
		super();
	}
}
