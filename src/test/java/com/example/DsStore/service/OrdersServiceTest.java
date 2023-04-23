package com.example.DsStore.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.entities.Orders;
import com.example.DsStore.entities.Product;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.repositories.CustomerRepo;
import com.example.DsStore.repositories.OrdersRepo;
import com.example.DsStore.repositories.ProductRepo;
import com.example.DsStore.serviceImpl.BackOrdersServiceImpl;
import com.example.DsStore.serviceImpl.OrdersServiceImpl;
import com.example.DsStore.serviceImpl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {

	@Mock
	private OrdersRepo ordersRepo;

	@Mock
	private CustomerRepo customerRepo;

	@Mock
	private ProductRepo productRepo;

	@Mock
	private ProductServiceImpl productServiceImpl;

	@Mock
	private BackOrdersServiceImpl backOrdersServiceImpl;

	@InjectMocks
	private OrdersServiceImpl ordersServiceImpl;

	private Orders orders1;
	private Orders orders2;
	List<Orders> ordersList = new ArrayList<>();
	private Customer customer1;
	private Customer customer2;
	List<Customer> customerList = new ArrayList<>();
	private Product product1;
	private Product product2;
	List<Product> productList = new ArrayList<>();

	/**
	 * This is used initalize the order1, order2, customer and product values before
	 * performing any Test.
	 */
	@BeforeEach
	void init() {
		customer1 = new Customer(1, "Amit", "Punjab", 12345);
		customer2 = new Customer(2, "Himanshu", "Chandigarh", 45678);
		customerList.add(customer1);
		customerList.add(customer2);
		product1 = new Product(1, "DairyMilk", "Chocolate", 150, new Date(28 - 10 - 2025), 25, true);
		product2 = new Product(2, "JellyBean", "Dessert", 200, new Date(03 - 06 - 2030), 40, true);
		productList.add(product1);
		productList.add(product2);
		orders1 = new Orders(1, new Date(), 5, customer1, product1);
		orders2 = new Orders(2, new Date(), 10, customer2, product2);
		ordersList.add(orders1);
		ordersList.add(orders2);
	}

	/**
	 * Create Order Test
	 * @throws ResourceNotFoundException 
	 * @throws IdNotFoundException 
	 *
	 */
	@Test
	void testCreateOrder() throws IdNotFoundException, ResourceNotFoundException {
		when(ordersRepo.save(any(Orders.class))).thenReturn(orders1);
		when(customerRepo.findById(anyInt())).thenReturn(Optional.of(customer1));
		when(productRepo.findById(anyInt())).thenReturn(Optional.of(product1));
		Orders newOrder = ordersServiceImpl.createOrder(orders1,orders1.getCustomer().getCustomerId(),orders1.getProduct().getProductId());
		assertNotNull(newOrder);
		assertEquals(orders1, newOrder);
		verify(ordersRepo).save(any(Orders.class));
	}

	/**
	 * Update Order Test
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	void testUpdateOrder() throws IdNotFoundException {
		when(ordersRepo.save(any(Orders.class))).thenReturn(orders1);
		when(ordersRepo.findById(anyInt())).thenReturn(Optional.of(orders1));
		when(customerRepo.findById(anyInt())).thenReturn(Optional.of(customer1));
		when(productRepo.findById(anyInt())).thenReturn(Optional.of(product1));
		
		orders1.setQuantity(50);
		Orders updateOrder = ordersServiceImpl.updateOrder(orders1, orders1.getCustomer().getCustomerId(),orders1.getProduct().getProductId(), orders1.getOrderId());
		
		assertNotNull(updateOrder);
		assertEquals(50, orders1.getQuantity());
		
		verify(ordersRepo).findById(anyInt());
		verify(ordersRepo).save(any(Orders.class));
	}

	/**
	 * Update Order negative Test
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	void testUpdateOrderException() {
		when(ordersRepo.findById(anyInt())).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> ordersServiceImpl.updateOrder(orders1, 1, 1, 1))
		                    .isInstanceOf(IdNotFoundException.class);
		
		verify(ordersRepo).findById(anyInt());
	}

	/**
	 * Get All Orders Test.
	 *
	 * @throws ResourceNotFoundException No data found
	 */
	@Test
	void tesGetAllOrders() throws ResourceNotFoundException {
		
		
		when(ordersRepo.findAll()).thenReturn(ordersList);
		
		assertNotNull(ordersList);
		assertEquals(2, ordersServiceImpl.getAllOrders().size());
		
		verify(ordersRepo).findAll();
	}

	/**
	 * Get Order by Id.
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	void testGetOrderById() throws IdNotFoundException {
		when(ordersRepo.findById(anyInt())).thenReturn(Optional.of(orders1));
		
		assertEquals(1, ordersServiceImpl.getOrderById(1).getOrderId());
		verify(ordersRepo).findById(anyInt());
	}

	/**
	 * Get Negative Order by Id
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	void testGetOrderByIdException() {
		when(ordersRepo.findById(anyInt())).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> ordersServiceImpl.getOrderById(1))
		                    .isInstanceOf(IdNotFoundException.class);
		
		verify(ordersRepo).findById(anyInt());
	}

	/**
	 * Delete Order Test
	 *
	 */
	@Test
	void testDeleteOrder() {
		when(ordersRepo.findById(anyInt())).thenReturn(Optional.of(orders1));
		doNothing().when(ordersRepo).delete(orders1);
		
		ordersServiceImpl.deleteOrder(orders1.getOrderId());
		
		verify(ordersRepo).findById(1);
		verify(ordersRepo).delete(orders1);
	}

}
