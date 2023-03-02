package com.example.DsStore.serviceImpl;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.repositories.CustomerRepo;
import com.example.DsStore.services.CustomerService;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	/**
	 * This method is used to create Customer to the database.
	 *
	 * @return savedCustomer
	 */
	@Override
	public Customer createCustomer(Customer customer) {
		Customer savedCustomer = this.customerRepo.save(customer);
		log.info("new customer created");
		return savedCustomer;
	}

	/**
	 * This method is used to update Customer in the database.
	 *
	 * @return updatedCustomer
	 * @throws IdNotFoundException No productId found
	 */
	@Override
	public Customer updateCustomer(Customer changesCustomer, Integer customerId) {

		Customer oldCustomer = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new IdNotFoundException("Customer", "customerId", customerId));
		oldCustomer.setCustomerName(changesCustomer.getCustomerName());
		oldCustomer.setCustomerAddress(changesCustomer.getCustomerAddress());
		oldCustomer.setCustomerNumber(changesCustomer.getCustomerNumber());

		Customer updatedCustomer = this.customerRepo.save(oldCustomer);
		log.info("Customer updated");
		return updatedCustomer;
	}

	/**
	 * This method is used to Get specific Customer from database based on id.
	 *
	 * @return customerFound
	 * @throws IdNotFoundException No productId found
	 */
	@Override
	public Customer getCustomerById(Integer customerId) {

		Customer customerFound = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new IdNotFoundException("Customer", "customerId", customerId));
		log.info("Customer by id found");
		return customerFound;
	}

	/**
	 * This method is used to Get All the Customers from database.
	 *
	 * @return customers
	 * @throws ResourceNotFoundException if No data found in database
	 */
	@Override
	public List<Customer> getAllCustomers() throws ResourceNotFoundException {
		List<Customer> customers = this.customerRepo.findAll();
		if (customers.isEmpty()) {
			log.error("Nothing found in Product ");
			throw new ResourceNotFoundException("No data present in database");
		}
		log.info(" Customer list found with total customers "+customers.size());
		return customers;
	}

	/**
	 * This method is used to delete the specific customer from the database based
	 * on customer id.
	 *
	 * @throws IdNotFoundException No productId found
	 */
	@Override
	public void deleteCustomer(Integer customerId) {

		Customer customerDelete = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new IdNotFoundException("Customer", "customerId", customerId));
		this.customerRepo.delete(customerDelete);
		log.info("Customer deleted with id "+customerId);
	}
}
