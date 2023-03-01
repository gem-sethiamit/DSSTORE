package com.example.DsStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.payloads.ApiResponse;
import com.example.DsStore.services.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	//POST - create customer
	@PostMapping("/")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
		Customer createCustomer = this.customerService.createCustomer(customer);
		return new ResponseEntity<>(createCustomer,HttpStatus.CREATED);
	}
	
	//PUT - update customer
	@PutMapping("/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable Integer customerId){
		Customer updatedCustomer = this.customerService.updateCustomer(customer, customerId);
		return ResponseEntity.ok(updatedCustomer);
	}
	
	//DELETE - delete customer
		@DeleteMapping("/{customerId}")
		public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer customerId){
			this.customerService.deleteCustomer(customerId);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Customer deleted successfully",true),HttpStatus.OK);
		}

		//GET ALL - get all customers 
		@GetMapping("/")
		public ResponseEntity<List<Customer>> getAllGoals(){
			return ResponseEntity.ok(this.customerService.getAllCustomers());
		}

		//GET - get customer
		@GetMapping("/{customerId}")
		public ResponseEntity<Customer>getSingleGoal(@PathVariable Integer customerId){
			return ResponseEntity.ok(this.customerService.getCustomerById(customerId));
		}

	
}
