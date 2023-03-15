package com.example.DsStore.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.example.DsStore.entities.Orders;
import com.example.DsStore.entities.Product;
import com.example.DsStore.serviceImpl.OrdersServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = OrdersController.class)
public class OrdersControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private OrdersServiceImpl ordersServiceImpl;

	private Orders orders1;
	private Orders orders2;
	private Customer customer;
	private Product product;

	/**
	 * This is used initalize the order1, order2, customer and product values before
	 * performing any Test.
	 */
	@BeforeEach
	void init() {
		customer = new Customer(1, "Amit", "Punjab", 12345);
		product = new Product(1, "DairyMilk", "Chocolate", 150, new Date(28 - 10 - 2025), 25, true);
		orders1 = new Orders(1, new Date(), 5, customer, product);
		orders2 = new Orders(2, new Date(), 10, customer, product);
	}

	/**
	 * Create Order Test
	 *
	 * @throws Exception
	 */
	@Test
	void createOrderTest() throws Exception {
		when(ordersServiceImpl.createOrder(any(Orders.class), anyInt(), anyInt())).thenReturn(orders1);

		this.mockMvc.perform(post("/api/customer/1/product/1/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orders1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.orderId", is(orders1.getOrderId())))
				.andExpect(jsonPath("$.quantity", is(orders1.getQuantity())))
				.andExpect(jsonPath("$.customer.customerName", is(orders1.getCustomer().getCustomerName())))
				.andExpect(jsonPath("$.product.productName", is(orders1.getProduct().getProductName())));
		
		verify(ordersServiceImpl).createOrder(any(Orders.class), anyInt(), anyInt());
	}

	/**
	 * Get All Orders Test.
	 *
	 * @throws Exception
	 */
	@Test
	void getAllOrdersTest() throws Exception {
		List<Orders> list = new ArrayList<>();
		list.add(orders1);
		list.add(orders2);

		when(ordersServiceImpl.getAllOrders()).thenReturn(list);

		this.mockMvc.perform(get("/api/orders")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(list.size())));

		verify(ordersServiceImpl).getAllOrders();
	}

	/**
	 * Get Order by Test.
	 *
	 * @throws Exception
	 */
	@Test
	void getOrderById() throws Exception{
		when(ordersServiceImpl.getOrderById(anyInt())).thenReturn(orders1);
		
		this.mockMvc.perform(get("/api/orders/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.quantity", is(orders1.getQuantity())))
			.andExpect(jsonPath("$.customer.customerName", is(orders1.getCustomer().getCustomerName())))
			.andExpect(jsonPath("$.product.productName", is(orders1.getProduct().getProductName())));
		
		verify(ordersServiceImpl).getOrderById(anyInt());
	}

	/**
	*Update Order Test
	*
	*@throws Exception
	*/
	@Test
	void updateOrderTest() throws Exception {
		when(ordersServiceImpl.updateOrder(any(Orders.class), anyInt(), anyInt(), anyInt())).thenReturn(orders1);
		
		this.mockMvc.perform(put("/api/customer/1/product/1/orders/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orders1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.orderId", is(orders1.getOrderId())))
				.andExpect(jsonPath("$.quantity", is(orders1.getQuantity())))
				.andExpect(jsonPath("$.customer.customerName", is(orders1.getCustomer().getCustomerName())))
				.andExpect(jsonPath("$.product.productName", is(orders1.getProduct().getProductName())));
		
		verify(ordersServiceImpl).updateOrder(any(Orders.class), anyInt(), anyInt(), anyInt());
	
	}

	@Test
	void deleteOrderTest() throws Exception {
		doNothing().when(ordersServiceImpl).deleteOrder(anyInt());
		this.mockMvc.perform(delete("/api/orders/1")).andExpect(status().isOk());
		verify(ordersServiceImpl, times(1)).deleteOrder(orders1.getOrderId());
		
		verify(ordersServiceImpl).deleteOrder(anyInt());
	}

}
