package com.example.DsStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.entities.Orders;
import com.example.DsStore.entities.Product;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {

	// Custom finder methods in repository

	List<Orders> findByCustomer(Customer customer);

	List<Orders> findByProduct(Product product);

}
