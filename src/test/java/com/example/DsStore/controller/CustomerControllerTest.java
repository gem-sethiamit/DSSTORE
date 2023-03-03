package com.example.DsStore.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.awt.PageAttributes.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.serviceImpl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CustomerServiceImpl customerService;

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
	 * Get All Customers Test
	 * @throws Exception
	 *
	 */
	
}



















