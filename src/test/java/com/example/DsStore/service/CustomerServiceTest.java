package com.example.DsStore.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.repositories.CustomerRepo;
import com.example.DsStore.serviceImpl.CustomerServiceImpl;
import com.example.DsStore.entities.Customer;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	CustomerRepo customerRepo;

	@InjectMocks
	CustomerServiceImpl customerService;

	/**
	 * Create Customer Test
	 *
	 */
	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer(1, "Amit", "Punjab", 12345);
		when(customerRepo.save(any(Customer.class))).thenReturn(customer);
		Customer newCustomer = customerService.createCustomer(customer);
		assertNotNull(newCustomer);
		assertEquals(customer, newCustomer);
	}

	/**
	 * Update Product Test
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	public void testUpdateProduct() throws IdNotFoundException {
		Customer customer = new Customer(1, "Amit", "Punjab", 12345);
		when(customerRepo.save(any(Customer.class))).thenReturn(customer);
		when(customerRepo.findById(anyInt())).thenReturn(Optional.of(customer));

		customer.setCustomerName("Pulkit");
		Customer updatedCustomer = customerService.updateCustomer(customer, customer.getCustomerId());

		assertNotNull(updatedCustomer);
		assertEquals("Pulkit", customer.getCustomerName());

	}

	/**
	 * Update Product negative Test
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	public void testUpdateProductException() {
		when(customerRepo.findById(anyInt())).thenReturn(Optional.empty());
		Customer customer = new Customer(1, "Amit", "Punjab", 12345);
		
		assertThatThrownBy(()-> customerService.updateCustomer(customer, 1))
		                .isInstanceOf(IdNotFoundException.class);
	}

	/**
	 * Get Customer by Id.
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	public void testGetCustomerById() throws IdNotFoundException {
		Customer customer = new Customer(1, "Amit", "Punjab", 12345);
		when(customerRepo.findById(anyInt())).thenReturn(Optional.of(customer));
		assertEquals(1, customerService.getCustomerById(1).getCustomerId());
		assertEquals("Amit", customerService.getCustomerById(1).getCustomerName());
		assertEquals("Punjab", customerService.getCustomerById(1).getCustomerAddress());
		assertEquals(12345, customerService.getCustomerById(1).getCustomerNumber());
	}

	/**
	 * Get Negative Customer by Id.
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	public void testGetCustomerByIdException() {
		when(customerRepo.findById(anyInt())).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> customerService.getCustomerById(1))
		                   .isInstanceOf(IdNotFoundException.class);
	}

	/**
	 * Get All Customers Test.
	 *
	 * @throws ResourceNotFoundException No data found
	 */

	@Test
	public void testGetAllCustomers() throws ResourceNotFoundException {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(new Customer(1, "Amit", "Punjab", 12345));
		customerList.add(new Customer(2, "Himanshu", "Chandigarh", 45678));
		when(customerRepo.findAll()).thenReturn(customerList);
		assertNotNull(customerList);
		assertEquals(2, customerService.getAllCustomers().size());
	}

	/**
	 * Get All Negative Customers Test.
	 *
	 * @throws ResourceNotFoundException No data found
	 */

	@Test
	public void testGetAllCustomersException() {
		List<Customer> customerList = new ArrayList<>();
		when(customerRepo.findAll()).thenReturn(customerList);

		assertThatThrownBy(() -> customerService.getAllCustomers()).isInstanceOf(ResourceNotFoundException.class);

	}

	/**
	 * Delete Product Test
	 *
	 * @throws IdNotFoundException       id not found
	 * @throws ResourceNotFoundException
	 */
	
	public void testDeleteCustomer() throws IdNotFoundException, ResourceNotFoundException {
		Customer customer = new Customer(2, "Amit", "Punjab", 12345);
		List<Customer> customerList = List.of(customer);

		when(customerRepo.findById(anyInt())).thenReturn(Optional.of(customer));
		when(customerRepo.save(any(Customer.class))).thenReturn(customer);
		customerService.deleteCustomer(2);
		when(customerRepo.findAll()).thenReturn(customerList);

		assertEquals(1, customerService.getAllCustomers().size());
		assertThatThrownBy(() -> customerService.getCustomerById(2)).isInstanceOf(IdNotFoundException.class);

	}
	
	 @Test
	    public void testDeleteCustomer1() {
		 Customer customer = new Customer(2, "Amit", "Punjab", 12345);
	        when(customerRepo.findById(anyInt())).thenReturn(Optional.of(customer));
	        doNothing().when(customerRepo).delete(customer);

	        customerService.deleteCustomer(customer.getCustomerId());

	        verify(customerRepo).findById(2);
	        verify(customerRepo).delete(customer);
	    }

}
