package com.example.DsStore.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.serviceImpl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = CustomerController.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CustomerServiceImpl customerServiceImpl;

	private Customer customer1;
	private Customer customer2;

	/**
	 * This is used initalize the customer1 and customer2 value before performing
	 * any Test.
	 */
	@BeforeEach
	void intit() {
		customer1 = new Customer(1, "Amit", "Punjab", 12345);
		customer2 = new Customer(2, "Himanshu", "Chandigarh", 45678);
	}

	/**
	 * Create Customers Test
	 * @throws Exception
	 *
	 */
	@Test
	void createCustomerTest() throws Exception {
		when(customerServiceImpl.createCustomer(any(Customer.class))).thenReturn(customer1);
		
		 this.mockMvc.perform(post("/api/customers/")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(customer1)))
		 			.andExpect(status().isCreated())
		 			.andExpect(jsonPath("$.customerName",is(customer1.getCustomerName())))
		 			.andExpect(jsonPath("$.customerAddress",is(customer1.getCustomerAddress())));
	}

	/**
	 * Get All Customers Test.
	 *
	 * @throws Exception
	 */
	@Test
	void getAllCustomersTest() throws Exception {
		List<Customer> list = new ArrayList<>();
		list.add(customer1);
		list.add(customer2);

		when(customerServiceImpl.getAllCustomers()).thenReturn(list);

		this.mockMvc.perform(get("/api/customers/")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(list.size())));
	}

	/**
	*Get Customer by Id Test.
	*
	*@throws Exception
	*/
	@Test
	void getcustomerByIdTest()throws Exception {
		when(customerServiceImpl.getCustomerById(anyInt())).thenReturn(customer1);
		
		this.mockMvc.perform(get("/api/customers/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.customerName",is(customer1.getCustomerName())))
		.andExpect(jsonPath("$.customerAddress",is(customer1.getCustomerAddress())));
	}

	/**
	*Update Customer Test
	*
	*@throws Exception
	*/
	@Test
	void updateCustomerTest() throws Exception {
		when(customerServiceImpl.updateCustomer(any(Customer.class), anyInt())).thenReturn(customer1);
		
		 this.mockMvc.perform(put("/api/customers/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(customer1)))
		 			.andExpect(status().isOk())
		 			.andExpect(jsonPath("$.customerName",is(customer1.getCustomerName())))
		 			.andExpect(jsonPath("$.customerAddress",is(customer1.getCustomerAddress())));
	}

	/**
	 * Delete Customer Test
	 *
	 * @throws Exception
	 */
	@Test
	void deleteCustomerTest() throws Exception {
		doNothing().when(customerServiceImpl).deleteCustomer(anyInt());

		this.mockMvc.perform(delete("/api/customers/1")).andExpect(status().isOk());

	}

}
