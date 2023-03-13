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
import com.example.DsStore.entities.Product;
import com.example.DsStore.exceptions.ApiResponse;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.services.ProductService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	/**
	 * Post requests to add new Product object to database.
	 *
	 * @return ResponseEntity <Product>
	 */
	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		Product createProduct = this.productService.createProduct(product);
		log.info("New Product Added");
		return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
	}

	/**
	 * Put request to update existing Product based on given Product Id.
	 * 
	 * @throws IdNotFoundException if Product id not found
	 * @return ResponseEntity <Product>
	 */
	@PutMapping("/{productId}")
	public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product, @PathVariable Integer productId)
			throws IdNotFoundException {
		Product updatedProduct = this.productService.updateProduct(product, productId);
		log.info("Product Updated");
		return ResponseEntity.ok(updatedProduct);
	}

	/**
	 * Put request to add count in existing Product inventory based on given Product
	 * Id.
	 * 
	 * @throws IdNotFoundException if Product id not found
	 * @return ResponseEntity <addProductCount>
	 */
	@PutMapping("/add/{productId}")
	public ResponseEntity<Product> addProductCount(@Valid @RequestBody Product product, @PathVariable Integer productId)
			throws IdNotFoundException {
		Product addProductCount = this.productService.countAddofProduct(product, productId);
		log.info("Product Quantity added");
		return ResponseEntity.ok(addProductCount);
	}

	/**
	 * Delete request to delete product from product database.
	 * 
	 * @throws IdNotFoundException if Product id not found
	 * @return ResponseEntity <ApiResponse>
	 */
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer productId) throws IdNotFoundException {
		this.productService.deleteProduct(productId);
		log.info("Product Deleted");
		return new ResponseEntity<ApiResponse>(new ApiResponse("Product deleted successfully", true), HttpStatus.OK);
	}

	/**
	 * Get All Products.
	 *
	 * @return ResponseEntity <List<Product>>
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/")
	public ResponseEntity<List<Product>> getAllProducts() throws ResourceNotFoundException {
		log.info("Get All Products");
		return ResponseEntity.ok(this.productService.getAllProducts());
	}

	/**
	 * Get request to get specific Product product ID.
	 *
	 * @return ResponseEntity <Product>
	 * @throws IdNotFoundException if Product id not found
	 */
	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable Integer productId) throws IdNotFoundException {
		log.info("Get Product by Id");
		return ResponseEntity.ok(this.productService.getProductbyId(productId));
	}

}
