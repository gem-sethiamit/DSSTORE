package com.example.DsStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.exceptions.ApiResponse;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.services.CustomerService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/**
	 * Post requests to add new Customer object to database.
	 *
	 * @return ResponseEntity <Customer>
	 */
	@PostMapping("/")
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
		Customer createCustomer = this.customerService.createCustomer(customer);
		log.info("New Customer Added");
		return new ResponseEntity<>(createCustomer, HttpStatus.CREATED);
	}

	/**
	 * Put request to update existing Customer based on given Customer Id.
	 * 
	 * @throws IdNotFoundException if Customer id not found
	 * @return ResponseEntity <Customer>
	 */
	@PutMapping("/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer,
			@PathVariable Integer customerId) throws IdNotFoundException {
		Customer updatedCustomer = this.customerService.updateCustomer(customer, customerId);
		log.info("Customer Updated");
		return ResponseEntity.ok(updatedCustomer);
	}

	/**
	 * Delete request to delete customer from customer database.
	 * 
	 * @throws IdNotFoundException if Customer id not found
	 * @return ResponseEntity <ApiResponse>
	 */
	@DeleteMapping("/{customerId}")
	public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Integer customerId) throws IdNotFoundException {
		this.customerService.deleteCustomer(customerId);
		log.info("Customer Deleted");
		return new ResponseEntity<ApiResponse>(new ApiResponse("Customer deleted successfully", true), HttpStatus.OK);
	}

	/**
	 * Get All Customers.
	 *
	 * @return ResponseEntity <List<Customer>>
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/")
	public ResponseEntity<List<Customer>> getAllCustomers() throws ResourceNotFoundException {
		log.info("Get All Customers");
		return ResponseEntity.ok(this.customerService.getAllCustomers());
	}

	/**
	 * Get request to get specific Customer customer ID.
	 *
	 * @return ResponseEntity <Customer>
	 * @throws IdNotFoundException if Customer id not found
	 */
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Integer customerId) throws IdNotFoundException {
		log.info("Get Customer by Id");
		return ResponseEntity.ok(this.customerService.getCustomerById(customerId));
	}

}
