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

import com.example.DsStore.entities.Orders;
import com.example.DsStore.exceptions.ApiResponse;
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

	/**
	 * Post requests to add new Order object to database.
	 *
	 * @return ResponseEntity <Order>
	 * @throws ResourceNotFoundException
	 * @throws IdNotFoundException
	 */
	@PostMapping("/customer/{customerId}/product/{productId}/orders")
	public ResponseEntity<Orders> createOrder(@RequestBody Orders order, @PathVariable Integer customerId,
			@PathVariable Integer productId) throws IdNotFoundException, ResourceNotFoundException {
		Orders createOrder = this.ordersService.createOrder(order, customerId, productId);
		log.info("New Order Added");
		return new ResponseEntity<>(createOrder, HttpStatus.CREATED);

	}

	/**
	 * Get request to get specific Order order ID.
	 *
	 * @return ResponseEntity <Order>
	 * @throws IdNotFoundException if Order id not found
	 */
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Orders> getOrder(@PathVariable Integer orderId) throws IdNotFoundException {
		log.info("Get Order by Id");
		return ResponseEntity.ok(this.ordersService.getOrderById(orderId));
	}

	/**
	 * Get All Orders.
	 *
	 * @return ResponseEntity <List<Order>>
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/orders")
	public ResponseEntity<List<Orders>> getAllOrders() throws ResourceNotFoundException {
		log.info("Get All Orders");
		return ResponseEntity.ok(this.ordersService.getAllOrders());
	}

	/**
	 * Put request to update existing Order based on given Order Id.
	 * 
	 * @throws IdNotFoundException if Order id not found
	 * @return ResponseEntity <Order>
	 */
	@PutMapping("/customer/{customerId}/product/{productId}/orders/{orderId}")
	public ResponseEntity<Orders> updateOrder(@RequestBody Orders order, @PathVariable Integer customerId,
			@PathVariable Integer productId, @PathVariable Integer orderId) {
		Orders updateOrder = this.ordersService.updateOrder(order, customerId, productId, orderId);
		log.info("Order Updated");
		return ResponseEntity.ok(updateOrder);

	}

	/**
	 * Delete request to delete order from order database.
	 * 
	 * @throws IdNotFoundException if Order id not found
	 * @return ResponseEntity <ApiResponse>
	 */
	@DeleteMapping("/orders/{orderId}")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Integer orderId) throws IdNotFoundException {
		this.ordersService.delteOrder(orderId);
		log.info("Order Deleted");
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order deleted successfully", true), HttpStatus.OK);
	}

}
