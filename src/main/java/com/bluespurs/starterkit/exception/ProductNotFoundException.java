package com.bluespurs.starterkit.exception;

/**
 * Exception describes "product not found" situation,
 * wraps name of not found product  
 */
public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5582075798531000716L;
	private String name;

	public ProductNotFoundException(String name) {
		super();
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
