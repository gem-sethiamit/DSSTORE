package com.example.DsStore.controller;

import com.example.DsStore.entities.BackOrders;
import com.example.DsStore.entities.Customer;
import com.example.DsStore.entities.Product;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.serviceImpl.BackOrdersServiceImpl;
import com.example.DsStore.services.BackOrdersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = BackOrdersController.class)
public class BackOrdersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private BackOrdersServiceImpl backOrdersServiceImpl;

	private BackOrders backOrder1;
	private BackOrders backOrder2;
	private Customer customer;
	private Product product;
	private List<BackOrders> backOrderList = new ArrayList<>();

	/**
	 * This is used initialize the backOrder1, backOrder2 values before performing
	 * any Test.
	 */
	@BeforeEach
	void init() {
		customer = new Customer(1, "Amit", "Punjab", 12345);
		product = new Product(1, "DairyMilk", "Chocolate", 150, new Date(28 - 10 - 2025), 25, true);
		backOrder1 = new BackOrders(1, new Date(), 5, product, customer);
		backOrder2 = new BackOrders(2, new Date(), 10, product, customer);
		backOrderList.add(backOrder1);
		backOrderList.add(backOrder2);
	}

	/**
     * Get All Backorders test
     *
     * @throws Exception
     */
    @Test
    void testGetAllbackOrders() throws Exception {
        
        when(backOrdersServiceImpl.getAllbackOrders()).thenReturn(backOrderList);

        
        this.mockMvc.perform(get("/api/backOrders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(backOrderList.size())));

       
        verify(backOrdersServiceImpl).getAllbackOrders();
    }

	/**
     * Get backorder test
     *
     * @throws Exception
     */
    @Test
    void testGetBackOrder() throws Exception {
        when(backOrdersServiceImpl.getBackOrderById(anyInt())).thenReturn(backOrder1);

       
        mockMvc.perform(get("/api/backOrders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(backOrder1.getQuantity())))
    			.andExpect(jsonPath("$.customer.customerName", is(backOrder1.getCustomer().getCustomerName())))
    			.andExpect(jsonPath("$.product.productName", is(backOrder1.getProduct().getProductName())));

       
        verify(backOrdersServiceImpl).getBackOrderById(anyInt());
    }
}
