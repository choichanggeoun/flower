package com.today.flower.orderlist;

public class OutOfStockException extends RuntimeException {

	public OutOfStockException(String message) {
		super(message);
	}
}
