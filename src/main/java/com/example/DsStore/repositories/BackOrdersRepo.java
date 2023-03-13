package com.example.DsStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.DsStore.entities.BackOrders;

@Repository
public interface BackOrdersRepo extends JpaRepository<BackOrders, Integer> {

}
