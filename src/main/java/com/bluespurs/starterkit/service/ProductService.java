package com.bluespurs.starterkit.service;

import com.bluespurs.starterkit.pojo.Product;

/**
 * ProductService interface, is used for search of the cheapest products in the list of registered sellers 
 */
public interface ProductService {
	
	/**
	 * Returns cheapest product from the list of registered sellers by the given name
	 * @param name product to search for
	 * @return found product
	 */
	public Product searchCheapest(String name); 

}
