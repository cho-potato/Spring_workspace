package com.edu.springboard.exception;

public class CategoryException extends RuntimeException {
	
	public CategoryException(String msg) {
		super();
	}

	public CategoryException(String msg, Throwable e) {
		super(msg, e);
	}
}
