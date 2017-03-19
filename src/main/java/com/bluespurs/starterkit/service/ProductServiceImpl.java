package com.bluespurs.starterkit.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bluespurs.starterkit.exception.BadHttpResponseExcpetion;
import com.bluespurs.starterkit.exception.ProductNotFoundException;
import com.bluespurs.starterkit.pojo.Product;
import com.bluespurs.starterkit.repository.Seller;
import com.bluespurs.starterkit.util.JsonNodeHelper;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * ProductService implementation, searching for requested product in the list of registered sellers  
 */
@Service ("productService")
public class ProductServiceImpl implements ProductService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	List<Seller> sellers;
	
	/**
	 * Looks for the cheapest product by the given name
	 * in the list of registered sellers
	 */
	@Override
	public Product searchCheapest(String name) {
		LOGGER.info("Product service searching for cheapest: " + name);
		CheapestInfoWrapper cheapestInfoWrapper = null;
		for (Seller seller : sellers) {
			ResponseEntity<JsonNode> found = restTemplate.getForEntity(seller.getCheapestProductUrlTemplate(), JsonNode.class, getProductUrlTemplateParameters(name,  seller.getApiKey()));
			checkResponseAndSignalError(found, seller, name);
			if (!JsonNodeHelper.isProductFound(found.getBody(), seller))
				continue; // not found for this seller, move on to the next one
			if (cheapestInfoWrapper == null) {
				cheapestInfoWrapper = new CheapestInfoWrapper(found.getBody(), seller);
				continue; // the first found is cheapest now
			}
			if (JsonNodeHelper.getBestPrice(found.getBody(), seller) < JsonNodeHelper.getBestPrice(cheapestInfoWrapper.getProductJson(), cheapestInfoWrapper.getProductSeller())) {
				cheapestInfoWrapper = new CheapestInfoWrapper(found.getBody(), seller);
			}
		}
		if (cheapestInfoWrapper == null) {
			throw new ProductNotFoundException(name); //not only to return message, but also to set return HTTP status to 404
		}
		// I do not map JSON, returned from rest template directly to Product because it would
		// require separate mapper for each new seller - it can make adding new seller more difficult   
		Product product = createFrom(cheapestInfoWrapper.getProductJson(), cheapestInfoWrapper.getProductSeller());
		LOGGER.info("Cheapest product found: " + product.toString());
		return product;
	}

	/**
	 * Creates product class instance from JsonNode and Seller
	 * @param jsonNode
	 * @param seller
	 * @return Product class
	 */
	Product createFrom (JsonNode jsonNode, Seller seller) {
		Product product = new Product();
		product.setProductName(JsonNodeHelper.getProductName(jsonNode, seller));
		product.setBestPrice(JsonNodeHelper.getBestPrice(jsonNode, seller));
		product.setLocation(seller.getName());
		return product;
	}

	/**
	 * Method returns map, containing parameters for product URL template
	 * @param productName product name 
	 * @param apiKey seller API key
	 * @return map containing product name and API key for product search
	 */
	Map<String, String> getProductUrlTemplateParameters(String productName, String apiKey) {
		Map<String, String> urlParameters = new HashMap<String, String>();
		urlParameters.put("name", productName);
		urlParameters.put("key", apiKey);
		return urlParameters;
	}
	
	/**
	 * Method checks response and throws exception if something goes wrong.
	 * Throwing exception here is arguable - should we continue search through other sellers
	 * if one of them is broken? Or should we first understand what had happened 
	 * and why seller does not answer with OK status?
	 * @param response
	 */
	void checkResponseAndSignalError(ResponseEntity<JsonNode> response, Seller seller, String name) {
		if (response.getStatusCode() != HttpStatus.OK) {
			new BadHttpResponseExcpetion(response.getStatusCode(), MessageFormat.format("Seller {seller} returned NOT-OK HTTP response code trying to search product {product}", seller.getName(), name));
		}
	}
	
	/**
	 * Class wraps information about found cheapest product 
	 * and its seller. Used mostly for convenience.
	 */
	class CheapestInfoWrapper {
		JsonNode productJson;
		Seller productSeller;
		public CheapestInfoWrapper(JsonNode productJson, Seller productSeller) {
			super();
			this.productJson = productJson;
			this.productSeller = productSeller;
		}
		public JsonNode getProductJson() {
			return productJson;
		}
		public void setProductJson(JsonNode productJson) {
			this.productJson = productJson;
		}
		public Seller getProductSeller() {
			return productSeller;
		}
		public void setProductSeller(Seller productSeller) {
			this.productSeller = productSeller;
		}
	}
}
