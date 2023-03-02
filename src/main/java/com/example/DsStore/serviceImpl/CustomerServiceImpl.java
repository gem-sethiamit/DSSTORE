package com.example.DsStore.serviceImpl;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.repositories.CustomerRepo;
import com.example.DsStore.services.CustomerService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public Customer createCustomer(Customer customer) {
		Customer savedCustomer = this.customerRepo.save(customer);
		return savedCustomer;
	}

	@Override
	public Customer updateCustomer(Customer changesCustomer, Integer customerId) {

		Customer oldCustomer = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));
		oldCustomer.setCustomerName(changesCustomer.getCustomerName());
		oldCustomer.setCustomerAddress(changesCustomer.getCustomerAddress());
		oldCustomer.setCustomerNumber(changesCustomer.getCustomerNumber());

		Customer updatedCustomer = this.customerRepo.save(oldCustomer);

		return updatedCustomer;
	}

	@Override
	public Customer getCustomerById(Integer customerId) {

		Customer customerFound = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));

		return customerFound;
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = this.customerRepo.findAll();

		return customers;
	}

	@Override
	public void deleteCustomer(Integer customerId) {

		Customer customerDelete = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));
		this.customerRepo.delete(customerDelete);
	}
}
