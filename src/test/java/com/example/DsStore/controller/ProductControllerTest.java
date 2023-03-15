package com.example.DsStore.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.entities.Product;
import com.example.DsStore.serviceImpl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ProductServiceImpl productServiceImpl;

	private Product product1;
	private Product product2;

	/**
	 * This is used initalize the product1 and product2 value before performing any
	 * Test.
	 */
	@BeforeEach
	void intit() {
		product1 = new Product(1, "DairyMilk", "Chocolate", 150, new Date(28 - 10 - 2025), 25, true);
		product2 = new Product(2, "JellyBean", "Dessert", 200, new Date(03 - 06 - 2030), 40, true);
	}

	/**
	 * Create Products Test
	 * @throws Exception
	 *
	 */
	@Test
	void createProducttest()throws Exception{
		when(productServiceImpl.createProduct(any(Product.class))).thenReturn(product1);
		
		 this.mockMvc.perform(post("/api/products/")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(product1)))
		 			.andExpect(status().isCreated())
		 			.andExpect(jsonPath("$.productName",is(product1.getProductName())))
		 			.andExpect(jsonPath("$.productDesc",is(product1.getProductDesc())));
	}

	/**
	 * Get All Products Test.
	 *
	 * @throws Exception
	 */
	@Test
	void getAllProductsTest() throws Exception {
		List<Product> list = new ArrayList<>();
		list.add(product1);
		list.add(product2);

		when(productServiceImpl.getAllProducts()).thenReturn(list);

		this.mockMvc.perform(get("/api/products/")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(list.size())));

	}

	/**
	*Get Product by Id Test.
	*
	*@throws Exception
	*/
	@Test
	void getProductByIdTest() throws Exception {
		when(productServiceImpl.getProductbyId(anyInt())).thenReturn(product1);
		
		this.mockMvc.perform(get("/api/products/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.productName",is(product1.getProductName())))
		.andExpect(jsonPath("$.productDesc",is(product1.getProductDesc())));
	}

	/**
	*Update Product Test
	*
	*@throws Exception
	*/
	@Test
	void updateProductTest() throws Exception{
		when(productServiceImpl.updateProduct(any(Product.class), anyInt())).thenReturn(product1);
		
		 this.mockMvc.perform(put("/api/products/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(product1)))
		 			.andExpect(status().isOk())
		 			.andExpect(jsonPath("$.productName",is(product1.getProductName())))
		 			.andExpect(jsonPath("$.productDesc",is(product1.getProductDesc())));
	}

	/**
	 * Delete Product Test
	 *
	 * @throws Exception
	 */
	@Test
	void deleteProductTest() throws Exception {
		doNothing().when(productServiceImpl).deleteProduct(anyInt());

		this.mockMvc.perform(delete("/api/products/1")).andExpect(status().isOk());
	}

}
