package com.example.DsStore.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import com.example.DsStore.entities.BackOrders;
import com.example.DsStore.entities.Customer;
import com.example.DsStore.entities.Orders;
import com.example.DsStore.entities.Product;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.repositories.BackOrdersRepo;
import com.example.DsStore.repositories.CustomerRepo;
import com.example.DsStore.repositories.OrdersRepo;
import com.example.DsStore.repositories.ProductRepo;
import com.example.DsStore.serviceImpl.BackOrdersServiceImpl;
import com.example.DsStore.serviceImpl.OrdersServiceImpl;
import com.example.DsStore.serviceImpl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BackOrdersServiceTest {

	@Mock
	private BackOrdersRepo backOrdersRepo;

	@Mock
	private OrdersRepo ordersRepo;

	@Mock
	private CustomerRepo customerRepo;

	@Mock
	private ProductRepo productRepo;

	@Mock
	private ProductServiceImpl productServiceImpl;

	@InjectMocks
	private BackOrdersServiceImpl backOrdersServiceImpl;

	private BackOrders backorders1;
	private BackOrders backorders2;
	List<BackOrders> backordersList = new ArrayList<>();
	private Customer customer1;
	private Product product1;

	/**
	 * This is used initalize the backorder1, backorder2, customer and product values before
	 * performing any Test.
	 */
	@BeforeEach
	void init() {
		customer1 = new Customer(1, "Amit", "Punjab", 12345);

		product1 = new Product(1, "DairyMilk", "Chocolate", 150, new Date(28 - 10 - 2025), 25, true);

		backorders1 = new BackOrders(1, new Date(), 5, product1, customer1);
		backorders2 = new BackOrders(2, new Date(), 10, product1, customer1);
		backordersList.add(backorders1);
		backordersList.add(backorders2);
	}

	/**
	 * Create BackOrder Test
	 * @throws ResourceNotFoundException 
	 * @throws IdNotFoundException 
	 *
	 */
	@Test
	void testCreatebackOrder() {
		when(backOrdersRepo.save(any(BackOrders.class))).thenReturn(backorders1);
		 BackOrders savedBackOrder  = backOrdersServiceImpl.createBackOrdersbyOrder(customer1, product1, 10);
		
	        assertEquals(customer1, savedBackOrder.getCustomer());
	        assertEquals(product1, savedBackOrder.getProduct());
	        assertEquals(10, savedBackOrder.getQuantity());
	        verify(backOrdersRepo).save(any(BackOrders.class));
	}

	/**
	 * Get All backOrders Test.
	 *
	 * @throws ResourceNotFoundException No data found
	 */
	@Test
	void tesGetAllBackOrders() throws ResourceNotFoundException {
		
		
		when(backOrdersRepo.findAll()).thenReturn(backordersList);
		
		assertNotNull(backordersList);
		assertEquals(2, backOrdersServiceImpl.getAllbackOrders().size());
		
		verify(backOrdersRepo).findAll();
	}

	/**
	 * Get BackOrder by Id.
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	void testGetBackOrderById() throws IdNotFoundException {
		when(backOrdersRepo.findById(anyInt())).thenReturn(Optional.of(backorders1));
		
		assertEquals(1, backOrdersServiceImpl.getBackOrderById(1).getBackOrderId());
		verify(backOrdersRepo).findById(anyInt());
	}

	/**
	 * Get Negative backOrders by Id
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	void testGetOrderByIdException() {
		when(backOrdersRepo.findById(anyInt())).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> backOrdersServiceImpl.getBackOrderById(1))
		                    .isInstanceOf(IdNotFoundException.class);
		
		verify(backOrdersRepo).findById(anyInt());
	}

	/**
	 * Delete backOrders Test
	 *
	 */
	@Test
	void testDeleteOrder() {
		when(backOrdersRepo.findById(anyInt())).thenReturn(Optional.of(backorders1));
		doNothing().when(backOrdersRepo).delete(backorders1);
		
		backOrdersServiceImpl.delteOrder(backorders1.getBackOrderId());
		
		verify(backOrdersRepo).findById(anyInt());
		verify(backOrdersRepo).delete(backorders1);
	}

}
