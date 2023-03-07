package com.example.DsStore.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.entities.Product;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.repositories.ProductRepo;
import com.example.DsStore.services.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	/**
	 * This method is used to create Product to the database.
	 *
	 * @return savedProduct
	 */
	@Override
	public Product createProduct(Product product) {
		Product savedProduct = this.productRepo.save(product);
		log.info("new product created");
		return savedProduct;
	}

	/**
	 * This method is used to update Product in the database.
	 *
	 * @return updatedProduct
	 * @throws IdNotFoundException No productId found
	 */
	@Override
	public Product updateProduct(Product changesProduct, Integer productId) throws IdNotFoundException {
		Product oldProduct = this.productRepo.findById(productId)
				.orElseThrow(() -> new IdNotFoundException("Product", "Product Id", productId));
		oldProduct.setProductName(changesProduct.getProductName());
		oldProduct.setProductDesc(changesProduct.getProductDesc());
		oldProduct.setPrice(changesProduct.getPrice());
		oldProduct.setExpiry(changesProduct.getExpiry());
		oldProduct.setCount(changesProduct.getCount());
		oldProduct.setAvalibity(changesProduct.isAvalibity());

		Product updatedProduct = this.productRepo.save(oldProduct);
		log.info("ProductUpdated");
		return updatedProduct;
	}

	/**
	 * This method is used to Get specific Product from database based on id.
	 *
	 * @return productFound
	 * @throws IdNotFoundException No productId found
	 */
	@Override
	public Product getProductbyId(Integer productId) throws IdNotFoundException {
		Product productFound = this.productRepo.findById(productId)
				.orElseThrow(() -> new IdNotFoundException("Product", "Product Id", productId));
		log.info("Product by id found");
		return productFound;
	}

	/**
	 * This method is used to Get All the Products from database.
	 *
	 * @return products
	 * @throws ResourceNotFoundException if No data found in database
	 */
	@Override
	public List<Product> getAllProducts() throws ResourceNotFoundException {
		List<Product> products = this.productRepo.findAll();
		if (products.isEmpty()) {
			log.error("Nothing found in products ");
			throw new ResourceNotFoundException("No data present in database");
		}
		log.info(" Product list found with total products " + products.size());
		return products;
	}

	/**
	 * This method is used to delete the specific product from the database based on
	 * product id.
	 *
	 * @throws IdNotFoundException No productId found
	 */
	@Override
	public void deleteProduct(Integer productId) throws IdNotFoundException {
		Product productDelete = this.productRepo.findById(productId)
				.orElseThrow(() -> new IdNotFoundException("Product", "Product Id", productId));
		this.productRepo.delete(productDelete);
		log.info("Product deleted with id " + productId);

	}

}
