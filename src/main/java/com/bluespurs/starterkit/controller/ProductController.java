package com.bluespurs.starterkit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bluespurs.starterkit.pojo.Product;
import com.bluespurs.starterkit.service.ProductService;

@RestController
public class ProductController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;
    
    /**
     * The index page returns a simple String message to indicate if everything is working properly.
     * The method is mapped to "/product/search" as a GET request with required parameter "name"
     */
    @RequestMapping(path="/product/search", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Product search(@RequestParam(value="name", required = true) String name) {
    	LOGGER.info("Visiting product search page, looking for " + name);
        return productService.searchCheapest(name);
    }
}
