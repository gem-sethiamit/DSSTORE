package com.example.DsStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DsStore.entities.Orders;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.services.OrdersService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;
	

	//create
	@PostMapping("/customer/{customerId}/product/{productId}/orders")
	public ResponseEntity<Orders> createOrder(@RequestBody Orders order, @PathVariable Integer customerId,
			@PathVariable Integer productId) {
		Orders createOrder = this.ordersService.createOrder(order, customerId, productId);
		log.info("New Order Added");
		return new ResponseEntity<>(createOrder, HttpStatus.CREATED);

	}
	
	//get by orderid
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Orders> getOrder(@PathVariable Integer orderId) throws IdNotFoundException{
		log.info("Get Order by Id");
		return ResponseEntity.ok(this.ordersService.getOrderById(orderId));
	}
	
	//get by customerid
		@GetMapping("/customer/{customerId}/orders")
		public ResponseEntity<List<Orders>> getByCustomerId(@PathVariable Integer customerId) throws IdNotFoundException{
			log.info("Get Order by Id");
			return ResponseEntity.ok(this.ordersService.getOrdersByCustomers(customerId));
		}
	
	//get All
	@GetMapping("/orders")
	public ResponseEntity<List<Orders>> getAllOrders() throws ResourceNotFoundException{
		log.info("Get All Orders");
		return ResponseEntity.ok(this.ordersService.getAllOrders());
	}

}
