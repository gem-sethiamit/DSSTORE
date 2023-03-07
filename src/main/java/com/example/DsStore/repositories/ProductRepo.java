package com.example.DsStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.DsStore.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
