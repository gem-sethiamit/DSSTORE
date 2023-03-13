package com.example.DsStore.services;

import java.util.List;

import com.example.DsStore.entities.Product;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;

public interface ProductService {

	Product createProduct(Product product);

	Product updateProduct(Product product, Integer productId) throws IdNotFoundException;

	Product countAddofProduct(Product product, Integer productId) throws IdNotFoundException;

	Product getProductbyId(Integer productId) throws IdNotFoundException;

	List<Product> getAllProducts() throws ResourceNotFoundException;

	void deleteProduct(Integer productId) throws IdNotFoundException;
}
