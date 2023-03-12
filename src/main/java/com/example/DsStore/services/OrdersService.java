package com.example.DsStore.services;

import java.util.List;

import com.example.DsStore.entities.Orders;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;

import jakarta.persistence.criteria.CriteriaBuilder.In;

public interface OrdersService {

	Orders createOrder(Orders orders, Integer customerId, Integer productId) throws IdNotFoundException, ResourceNotFoundException;

	Orders updateOrder(Orders changeOrder, Integer customerId, Integer productId, Integer orderId)
			throws IdNotFoundException;

	Orders getOrderById(Integer orderId) throws IdNotFoundException;

	List<Orders> getAllOrders() throws ResourceNotFoundException;

	void delteOrder(Integer orderId) throws IdNotFoundException;

}
