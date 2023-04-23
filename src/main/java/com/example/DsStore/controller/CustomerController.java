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
import com.example.DsStore.exceptions.ApiErrorResponse;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.services.CustomerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	 * @param customer contains customer details
	 * @return ResponseEntity createCustomer
	 */
	@Operation(summary = "Create a new customer", description = "Creates a new customer with the provided details.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Customer created successfully", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PostMapping("/")
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
		Customer createCustomer = this.customerService.createCustomer(customer);
		log.info("New Customer Added");
		return new ResponseEntity<>(createCustomer, HttpStatus.CREATED);
	}

	/**
	 * Put request to update existing Customer based on given Customer Id.
	 * 
	 * @param customer contains customer details
	 * @throws IdNotFoundException if Customer id not found
	 * @return ResponseEntity updatedCustomer
	 */
	@Operation(summary = "Update an existing customer", description = "Update an existing customer with the given details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Customer updated successfully", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
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
	 * @return ResponseEntity <ApiErrorResponse>
	 */
	@Operation(summary = "Delete a customer", description = "Delete the customer with the given ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Customer deleted successfully", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@DeleteMapping("/{customerId}")
	public ResponseEntity<ApiErrorResponse> deleteCustomer(@PathVariable Integer customerId)
			throws IdNotFoundException {
		this.customerService.deleteCustomer(customerId);
		log.info("Customer Deleted");
		return new ResponseEntity<ApiErrorResponse>(new ApiErrorResponse("Customer deleted successfully", true),
				HttpStatus.OK);
	}

	/**
	 * Get All Customers.
	 *
	 * @return ResponseEntity <List<Customer>>
	 * @throws ResourceNotFoundException
	 */
	@Operation(summary = "Get all customers", description = "Retrieve a list of all customers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successful operation", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
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
	@Operation(summary = "Get customer by ID", description = "Retrieve the customer with the specified ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successful operation", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Integer customerId) throws IdNotFoundException {
		log.info("Get Customer by Id");
		return ResponseEntity.ok(this.customerService.getCustomerById(customerId));
	}

}
