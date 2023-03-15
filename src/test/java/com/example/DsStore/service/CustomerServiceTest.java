package com.example.DsStore.service;

import java.util.List;
import java.util.Optional;

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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.repositories.CustomerRepo;
import com.example.DsStore.serviceImpl.CustomerServiceImpl;
import com.example.DsStore.entities.Customer;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@Mock
	CustomerRepo customerRepo;

	@InjectMocks
	CustomerServiceImpl customerServiceImpl;

	/**
	 * Create Customer Test
	 *
	 */
	@Test
	void testCreateCustomer() {
		Customer customer = new Customer(1, "Amit", "Punjab", 12345);
		when(customerRepo.save(any(Customer.class))).thenReturn(customer);
		Customer newCustomer = customerServiceImpl.createCustomer(customer);
		assertNotNull(newCustomer);
		assertEquals(customer, newCustomer);
		verify(customerRepo).save(any(Customer.class));
	}

	/**
	 * Update Customer Test
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	void testUpdateCustomer() throws IdNotFoundException {
		Customer customer = new Customer(1, "Amit", "Punjab", 12345);
		when(customerRepo.save(any(Customer.class))).thenReturn(customer);
		when(customerRepo.findById(anyInt())).thenReturn(Optional.of(customer));

		customer.setCustomerName("Pulkit");
		Customer updatedCustomer = customerServiceImpl.updateCustomer(customer, customer.getCustomerId());

		assertNotNull(updatedCustomer);
		assertEquals("Pulkit", customer.getCustomerName());

		verify(customerRepo).findById(anyInt());
		verify(customerRepo).save(any(Customer.class));

	}

	/**
	 * Update Customer negative Test
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	 void testUpdateCustomerException() {
		when(customerRepo.findById(anyInt())).thenReturn(Optional.empty());
		Customer customer = new Customer(1, "Amit", "Punjab", 12345);
		
		assertThatThrownBy(()-> customerServiceImpl.updateCustomer(customer, 1))
		                .isInstanceOf(IdNotFoundException.class);
		
		verify(customerRepo).findById(anyInt());
	}

	/**
	 * Get Customer by Id.
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	void testGetCustomerById() throws IdNotFoundException {
		Customer customer = new Customer(1, "Amit", "Punjab", 12345);

		when(customerRepo.findById(anyInt())).thenReturn(Optional.of(customer));

		assertEquals(1, customerServiceImpl.getCustomerById(1).getCustomerId());

		verify(customerRepo).findById(anyInt());
	}

	/**
	 * Get Negative Customer by Id.
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	 void testGetCustomerByIdException() {
		when(customerRepo.findById(anyInt())).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> customerServiceImpl.getCustomerById(1))
		                   .isInstanceOf(IdNotFoundException.class);
		
		verify(customerRepo).findById(anyInt());
	}

	/**
	 * Get All Customers Test.
	 *
	 * @throws ResourceNotFoundException No data found
	 */

	@Test
	void testGetAllCustomers() throws ResourceNotFoundException {
		List<Customer> customerList = new ArrayList<>();

		customerList.add(new Customer(1, "Amit", "Punjab", 12345));
		customerList.add(new Customer(2, "Himanshu", "Chandigarh", 45678));

		when(customerRepo.findAll()).thenReturn(customerList);

		assertNotNull(customerList);
		assertEquals(2, customerServiceImpl.getAllCustomers().size());

		verify(customerRepo).findAll();
	}

	/**
	 * Get All Negative Customers Test.
	 *
	 * @throws ResourceNotFoundException No data found
	 */

	@Test
	void testGetAllCustomersException() {
		List<Customer> customerList = new ArrayList<>();
		when(customerRepo.findAll()).thenReturn(customerList);

		assertThatThrownBy(() -> customerServiceImpl.getAllCustomers()).isInstanceOf(ResourceNotFoundException.class);

		verify(customerRepo).findAll();

	}

	/**
	 * Delete Customer Test
	 *
	 */

	@Test
	void testDeleteCustomer() {
		Customer customer = new Customer(2, "Amit", "Punjab", 12345);
		when(customerRepo.findById(anyInt())).thenReturn(Optional.of(customer));
		doNothing().when(customerRepo).delete(customer);

		customerServiceImpl.deleteCustomer(customer.getCustomerId());

		verify(customerRepo).findById(2);
		verify(customerRepo).delete(customer);
	}

}
