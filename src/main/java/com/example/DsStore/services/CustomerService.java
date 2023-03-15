package com.example.DsStore.services;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CustomerService {
	Customer createCustomer(Customer customer);

	Customer updateCustomer(Customer customer, Integer customerId) throws IdNotFoundException;

	Customer getCustomerById(Integer customerId) throws IdNotFoundException;

	List<Customer> getAllCustomers() throws ResourceNotFoundException;

	void deleteCustomer(Integer customerId) throws IdNotFoundException;

}
