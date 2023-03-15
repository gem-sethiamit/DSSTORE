package com.example.DsStore.services;

import java.util.List;

import com.example.DsStore.entities.BackOrders;
import com.example.DsStore.entities.Customer;
import com.example.DsStore.entities.Product;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;

public interface BackOrdersService {

	BackOrders createBackOrdersbyOrder(Customer customerFound, Product productFound, Integer orderQuantity);

	BackOrders getBackOrderById(Integer backOrderId) throws IdNotFoundException;

	List<BackOrders> getAllbackOrders() throws ResourceNotFoundException;

	void delteOrder(Integer backOrderId) throws IdNotFoundException;

}
