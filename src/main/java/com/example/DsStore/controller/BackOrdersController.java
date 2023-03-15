package com.example.DsStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DsStore.entities.BackOrders;
import com.example.DsStore.entities.Orders;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.services.BackOrdersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/")
public class BackOrdersController {

	@Autowired
	private BackOrdersService backOrdersService;

	/**
	 * Get All BackOrders.
	 *
	 * @return ResponseEntity <List<BackOrders>>
	 * @throws ResourceNotFoundException
	 */
	@Operation(summary = "Get all Backorders", description = "Retrieve a list of all backorders in the system")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successful operation", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/backOrders")
	public ResponseEntity<List<BackOrders>> getAllbackOrders() throws ResourceNotFoundException {
		log.info("Get All backOrders");
		return ResponseEntity.ok(this.backOrdersService.getAllbackOrders());
	}

	/**
	 * Get request to get specific BackOrder by backOrder ID.
	 *
	 * @return ResponseEntity <backOrder>
	 * @throws IdNotFoundException if backOrder id not found
	 */
	@Operation(summary = "Get backorder by ID", description = "Returns an backorder based on the provided backorder ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "BackOrder found and returned.", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/backOrders/{backorderId}")
	public ResponseEntity<BackOrders> getBackOrder(@PathVariable Integer backorderId) throws IdNotFoundException {
		log.info("Get backOrder by Id");
		return ResponseEntity.ok(this.backOrdersService.getBackOrderById(backorderId));
	}
}
