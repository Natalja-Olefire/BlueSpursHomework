package com.bluespurs.starterkit.repository;

/**
 * Class describes particular seller and seller specific information.
 * For creating new seller, SellerBuilder must be used (see how it is done in  {@link com.bluespurs.starterkit.ApplicationConfig#sellers()}).
 */
public class Seller {
	String name;
	String apiKey;
	String cheapestProductUrlTemplate;

	// Path to the JSON node, that contains products
	// Complex paths should use "/" as a separator 
	String productsNode;

	// Name of the element, that contains product name
	String nameElement;
	
	// Name of the element, that contains product price 
	String bestPriceElement;

	public Seller(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getCheapestProductUrlTemplate() {
		return cheapestProductUrlTemplate;
	}

	public void setCheapestProductUrlTemplate(String cheapestProductUrlTemplate) {
		this.cheapestProductUrlTemplate = cheapestProductUrlTemplate;
	}

	
	public String getProductsNode() {
		return productsNode;
	}

	public void setProductsNode(String productsNode) {
		this.productsNode = productsNode;
	}

	public String getNameElement() {
		return nameElement;
	}

	public void setNameElement(String nameElement) {
		this.nameElement = nameElement;
	}

	public String getBestPriceElement() {
		return bestPriceElement;
	}

	public void setBestPriceElement(String bestPriceElement) {
		this.bestPriceElement = bestPriceElement;
	}

	@Override
	public String toString() {
		return "Seller [name=" + name + ", apiKey=" + apiKey + ", cheapestProductUrlTemplate="
				+ cheapestProductUrlTemplate + ", productsNode=" + productsNode + ", nameElement=" + nameElement
				+ ", bestPriceElement=" + bestPriceElement + "]";
	}


	public static final class SellerBuilder {
		private String name;
		private String apiKey;
		private String cheapestProductUrlTemplate;
		private String productsNode;
		private String nameElement;
		private String bestPriceElement;
		
		public SellerBuilder (final String name) {
			this.name = name;
		}
		
		public SellerBuilder apiKey (final String apiKey) {
			this.apiKey = apiKey;
			return this;
		}

		public SellerBuilder cheapestProductUrlTemplate (final String cheapestProductUrlTemplate) {
			this.cheapestProductUrlTemplate = cheapestProductUrlTemplate;
			return this;
		}
		
		public SellerBuilder productsNode (final String productsNode) {
			this.productsNode = productsNode;
			return this;
		}

		public SellerBuilder nameElement (final String nameElement) {
			this.nameElement = nameElement;
			return this;
		}
		
		public SellerBuilder bestPriceElement (final String bestPriceElement) {
			this.bestPriceElement = bestPriceElement;
			return this;
		}
		
		public Seller createSeller() {
			if (name == null) 
				throw new IllegalStateException ("Seller name cannot be null");
			Seller seller = new Seller(name);
			if (apiKey == null) 
				throw new IllegalStateException ("Seller api key cannot be null");
			seller.setApiKey(apiKey);
			if (cheapestProductUrlTemplate == null) 
				throw new IllegalStateException ("Seller cheapestProductUrlTemplate cannot be null");
			seller.setCheapestProductUrlTemplate(cheapestProductUrlTemplate);
			if (productsNode == null) 
				throw new IllegalStateException ("Seller productsNode cannot be null");
			seller.setProductsNode(productsNode);
			if (bestPriceElement == null) 
				throw new IllegalStateException ("Seller bestPriceElement cannot be null");
			seller.setBestPriceElement(bestPriceElement);
			if (nameElement == null) 
				throw new IllegalStateException ("Seller nameElement cannot be null");
			seller.setNameElement(nameElement);
			return seller;
		}
		
	}
}
