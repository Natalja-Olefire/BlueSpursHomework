package com.bluespurs.starterkit.util;

import com.bluespurs.starterkit.repository.Seller;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonNodeHelper {

	/**
	 * Method returns true if product is found in returned by seller JSON response
	 * @param jsonNode target JSON node
	 * @param seller seller, that contains information about path inside JSON
	 * @return true if product is found, and false otherwise
	 */
	public static boolean isProductFound(JsonNode jsonNode, Seller seller) {
		return (jsonNode.get(seller.getProductsNode()) != null && jsonNode.get(seller.getProductsNode()).size() > 0);
	}

	/**
	 * Convenient method for retrieving best price form JsonNode object 
	 * @param jsonNode target JSON node
	 * @param seller seller, that contains information about path inside JSON
	 * @return value for best price field
	 */
	public static double getBestPrice(JsonNode jsonNode, Seller seller) {
		return jsonNode.get(seller.getProductsNode()).iterator().next().get(seller.getBestPriceElement()).doubleValue();
	}
	
	/**
	 * Convenient method for retrieving product name form JsonNode object 
	 * @param jsonNode target JSON node
	 * @param seller seller, that contains information about path inside JSON
	 * @return value for product name field
	 */
	public static String getProductName(JsonNode jsonNode, Seller seller) {
		return jsonNode.get(seller.getProductsNode()).iterator().next().get(seller.getNameElement()).asText();
	}
}
