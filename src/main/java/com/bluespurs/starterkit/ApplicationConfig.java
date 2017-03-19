package com.bluespurs.starterkit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bluespurs.starterkit.repository.Seller;

@SpringBootApplication
@EnableCaching
public class ApplicationConfig extends SpringBootServletInitializer {
    @Value("${http.enable-cors}")
    private boolean enableCors;
    @Value("${base-package}")
    private String basePackage;

    // you can find it in properties file, 
    // but in real life it should come from environment variable
    @Value("${apikey.walmart}")
    private String apikeyWalmart;
    
    // you can find it in properties file, 
    // but in real life it should come from environment variable
    @Value("${apikey.bestbuy}")
    private String apikeyBestbuy;
    
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(ApplicationConfig.class);
    }

    @Bean
    public WebMvcConfigurerAdapter webConfig() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                super.addCorsMappings(registry);

                if (enableCors) {
                    registry.addMapping("/**")
                            .allowedMethods("GET", "POST", "PUT", "DELETE");
                }
            }
        };
    }

    /**
     * Bean contains collection of registered sellers.
     * The best way to create sellers is SellerBuilder (like it is used bellow).
     * Each seller has name, API key, URL template (with "{name}" and "{key}" placeholders),
     * name of the node there returned products are returned, name of the element there 
     * name of the product is returned, and name of the element there best price is returned.
     * The best way to add new seller is this method.
     */
    @Bean
    public List<Seller> sellers() {
    	List<Seller> sellers = new ArrayList<Seller>();
    	//walmart
    	sellers.add(new Seller.SellerBuilder("walmart")
    			.apiKey(apikeyWalmart)
    			.cheapestProductUrlTemplate("http://api.walmartlabs.com/v1/search?apiKey={key}&query={name}&sort=price&order=asc&numItems=1")
    			.productsNode("items")
    			.nameElement("name")
    			.bestPriceElement("salePrice")
    			.createSeller());

    	//bestbuy
    	sellers.add(new Seller.SellerBuilder("bestbuy")
    			.apiKey(apikeyBestbuy)
    			.cheapestProductUrlTemplate("https://api.bestbuy.com/v1/products(name={name}*)?format=json&show=name,regularPrice,salePrice,onSale&sort=salePrice.asc&pageSize=1&total=1&apiKey={key}")
    			.productsNode("products")
    			.nameElement("name")
    			.bestPriceElement("salePrice")
    			.createSeller());
    	
    	return sellers;
    }
    
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}    
}
