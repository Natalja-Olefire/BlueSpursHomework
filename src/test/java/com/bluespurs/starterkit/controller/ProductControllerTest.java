package com.bluespurs.starterkit.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import com.bluespurs.starterkit.UnitTest;
import com.bluespurs.starterkit.exception.ProductNotFoundException;
import com.bluespurs.starterkit.pojo.Product;
import com.bluespurs.starterkit.service.ProductService;
import com.bluespurs.starterkit.service.ProductServiceImpl;


@Category(UnitTest.class)
public class ProductControllerTest extends UnitTest {
    MockMvc mockMvc;
    ProductService productService; 
    ProductController productController = new ProductController();
    ErrorController errorController = new ErrorController();
    
    Product existingProduct;
    
    @Before
    public void setUp() {
        super.setUp();
        productService = Mockito.mock(ProductServiceImpl.class);
        existingProduct = new Product();
        existingProduct.setProductName("ipad");
        existingProduct.setBestPrice(12.20);
        existingProduct.setLocation("walmart");
        productController.productService = productService;
        mockMvc = MockMvcBuilders.standaloneSetup(productController).setControllerAdvice(errorController).build();
    }

    @Test
    public void testSearchExistingProduct() throws Exception {
    	when(productService.searchCheapest("ipad")).thenReturn(existingProduct);
        mockMvc.perform(get("/product/search").param("name", "ipad"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("productName", equalTo("ipad")))
            .andExpect(jsonPath("location", equalTo("walmart")));
    }

    @Test
    public void testSearchNotExistingProduct() throws Exception {
    	when(productService.searchCheapest("notexisting")).thenThrow(new ProductNotFoundException("not found"));
        mockMvc.perform(get("/product/search").param("name", "notexisting"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testBadRequest() throws Exception {
    	when(productService.searchCheapest("jkdsfdshfsuk;sdfiudsyf;/\\;sdfsd")).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        mockMvc.perform(get("/product/search").param("name", "jkdsfdshfsuk;sdfiudsyf;/\\;sdfsd"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testForbidden() throws Exception {
    	when(productService.searchCheapest("any")).thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));
        mockMvc.perform(get("/product/search").param("name", "any"))
            .andExpect(status().isForbidden());
    }
    
}
