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
import com.example.DsStore.exceptions.ApiErrorResponse;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.services.OrdersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	 * @param order contains order details
	 * @return ResponseEntity <Order>
	 * @throws ResourceNotFoundException
	 * @throws IdNotFoundException
	 */
	@Operation(summary = "Create a new order", description = "Creates a new order for a specific customer and product")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Order created successfully", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PostMapping("/customer/{customerId}/product/{productId}/orders")
	public ResponseEntity<Orders> createOrder(@Valid @RequestBody Orders order, @PathVariable Integer customerId,
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
	@Operation(summary = "Get order by ID", description = "Returns an order based on the provided order ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Order found and returned.", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
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
	@Operation(summary = "Get all orders", description = "Retrieve a list of all orders in the system")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successful operation", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/orders")
	public ResponseEntity<List<Orders>> getAllOrders() throws ResourceNotFoundException {
		log.info("Get All Orders");
		return ResponseEntity.ok(this.ordersService.getAllOrders());
	}

	/**
	 * Put request to update existing Order based on given Order Id.
	 * 
	 * @param order contains order details
	 * 
	 * @throws IdNotFoundException if Order id not found
	 * @return ResponseEntity <Order>
	 */
	@Operation(summary = "Update an existing order", description = "Update an existing order in the system")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Order updated successfully", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PutMapping("/customer/{customerId}/product/{productId}/orders/{orderId}")
	public ResponseEntity<Orders> updateOrder(@Valid @RequestBody Orders order, @PathVariable Integer customerId,
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
	@Operation(summary = "Delete a order by ID", description = "Delete a order from the system by its unique ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Order deleted successfully", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@DeleteMapping("/orders/{orderId}")
	public ResponseEntity<ApiErrorResponse> deleteOrder(@PathVariable Integer orderId) throws IdNotFoundException {
		this.ordersService.deleteOrder(orderId);
		log.info("Order Deleted");
		return new ResponseEntity<ApiErrorResponse>(new ApiErrorResponse("Order deleted successfully", true),
				HttpStatus.OK);
	}

}
