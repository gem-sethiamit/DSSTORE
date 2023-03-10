package com.example.DsStore.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.entities.Product;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.repositories.ProductRepo;
import com.example.DsStore.serviceImpl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@Mock
	ProductRepo productRepo;

	@InjectMocks
	ProductServiceImpl productServiceImpl;

	/**
	 * Create Product Test
	 *
	 */
	@Test
	public void testCreateProduct() {
		Product product = new Product(1, "DairyMilk", "Chocolate", 150, new Date(28 - 10 - 2025), 25, true, null);
		when(productRepo.save(any(Product.class))).thenReturn(product);
		Product newProduct = productServiceImpl.createProduct(product);
		assertNotNull(newProduct);
		assertEquals(product, newProduct);
		verify(productRepo).save(any(Product.class));

	}

	/**
	 * Update Product Test
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	public void testUpdateProduct() throws IdNotFoundException {
		Product product = new Product(1, "DairyMilk", "Chocolate", 150, new Date(28 - 10 - 2025), 25, true, null);
		when(productRepo.save(any(Product.class))).thenReturn(product);
		when(productRepo.findById(anyInt())).thenReturn(Optional.of(product));

		product.setProductName("kitKat");
		Product updatedProduct = productServiceImpl.updateProduct(product, product.getProductId());

		assertNotNull(updatedProduct);
		assertEquals("kitKat", product.getProductName());

		verify(productRepo).findById(anyInt());
		verify(productRepo).save(any(Product.class));
	}

	/**
	 * Update Product negative Test
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	public void testUpdateProductException() {
		when(productRepo.findById(anyInt())).thenReturn(Optional.empty());
		Product product = new Product(1, "DairyMilk", "Chocolate", 150,new Date(28-10-2025), 25, true, null);
		
		assertThatThrownBy(() -> productServiceImpl.updateProduct(product, 1))
							.isInstanceOf(IdNotFoundException.class);
		
		verify(productRepo).findById(anyInt());
	}

	/**
	 * Get Product by Id.
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	public void testGetProductById() throws IdNotFoundException {
		Product product = new Product(1, "DairyMilk", "Chocolate", 150, new Date(28 - 10 - 2025), 25, true, null);
		when(productRepo.findById(anyInt())).thenReturn(Optional.of(product));

		assertEquals(1, productServiceImpl.getProductbyId(1).getProductId());

		verify(productRepo).findById(anyInt());
	}

	/**
	 * Get Negative Product by Id.
	 *
	 * @throws IdNotFoundException id not found
	 */
	@Test
	public void testGetProductByIdException() {
		when(productRepo.findById(anyInt())).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> productServiceImpl.getProductbyId(1))
							.isInstanceOf(IdNotFoundException.class);
		
		verify(productRepo).findById(anyInt());
	}

	/**
	 * Get All Products Test.
	 *
	 * @throws ResourceNotFoundException No data found
	 */
	@Test
	public void testGetAllProducts() throws ResourceNotFoundException {
		List<Product> productList = new ArrayList<>();

		productList.add(new Product(1, "DairyMilk", "Chocolate", 150, new Date(28 - 10 - 2025), 25, true, null));
		productList.add(new Product(2, "JellyBean", "Dessert", 200, new Date(03 - 06 - 2030), 40, true, null));

		when(productRepo.findAll()).thenReturn(productList);

		assertNotNull(productList);
		assertEquals(2, productServiceImpl.getAllProducts().size());

		verify(productRepo).findAll();

	}

	/**
	 * Get All Negative Products Test.
	 *
	 * @throws ResourceNotFoundException No data found
	 */
	@Test
	public void testGetAllProductsException() {
		List<Product> productList = new ArrayList<>();
		when(productRepo.findAll()).thenReturn(productList);

		assertThatThrownBy(() -> productServiceImpl.getAllProducts()).isInstanceOf(ResourceNotFoundException.class);

		verify(productRepo).findAll();
	}

	/**
	 * Delete Product Test
	 *
	 */
	@Test
	public void testDeleteProduct() {
		Product product = new Product(2, "DairyMilk", "Chocolate", 150, new Date(28 - 10 - 2025), 25, true, null);
		when(productRepo.findById(anyInt())).thenReturn(Optional.of(product));

		productServiceImpl.deleteProduct(product.getProductId());

		verify(productRepo).findById(2);
		verify(productRepo).delete(product);

	}

}
