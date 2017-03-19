package com.bluespurs.starterkit.pojo;

/**
 * Class represents found product 
 */
public class Product {
	
	// Current implementation does not retrieve currency from the seller server, 
	// so all products are considered to be priced in CAD
	public static final String DEFAULT_CURRENCY = "CAD";
	
	String productName;
	double bestPrice;
	String currency = DEFAULT_CURRENCY;
	String location;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getBestPrice() {
		return bestPrice;
	}
	public void setBestPrice(double bestPrice) {
		this.bestPrice = bestPrice;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
