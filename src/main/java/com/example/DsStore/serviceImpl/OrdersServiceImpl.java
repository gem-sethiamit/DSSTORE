package com.example.DsStore.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.entities.Orders;
import com.example.DsStore.entities.Product;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.repositories.CustomerRepo;
import com.example.DsStore.repositories.OrdersRepo;
import com.example.DsStore.repositories.ProductRepo;
import com.example.DsStore.services.OrdersService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersRepo ordersRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private ProductRepo productRepo;

	@Override
	public Orders createOrder(Orders orders, Integer customerId, Integer productId) throws IdNotFoundException {

		Customer customerFound = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new IdNotFoundException("Customer", "Customer Id", customerId));

		Product productFound = this.productRepo.findById(productId)
				.orElseThrow(() -> new IdNotFoundException("Product", "Product Id", productId));

		orders.setOrderTimeStamp(new Date());
		orders.setCustomer(customerFound);
		orders.setProduct(productFound);

		Orders savedOrder = this.ordersRepo.save(orders);

		return savedOrder;
	}

	@Override
	public Orders updateOrder(Orders orders, Integer orderId) throws IdNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Orders getOrderById(Integer orderId) throws IdNotFoundException {
		Orders orderFound = this.ordersRepo.findById(orderId)
				.orElseThrow(() -> new IdNotFoundException("Order", "Order Id", orderId));
		log.info("Order by id found");
		return orderFound;
	}

	@Override
	public List<Orders> getAllOrders() throws ResourceNotFoundException {
		List<Orders> orders = this.ordersRepo.findAll();
		log.info("Order list found size " + orders.size());
		return orders;
	}

	@Override
	public void delteOrder(Integer orderId) throws IdNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Orders> getOrdersByProduct(Integer productId) {
		Product productFound = this.productRepo.findById(productId)
				.orElseThrow(() -> new IdNotFoundException("Product", "Product Id", productId));

		List<Orders> orders = this.ordersRepo.findByProduct(productFound);

		return orders;
	}

	@Override
	public List<Orders> getOrdersByCustomers(Integer customerId) {
		Customer customerFound = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new IdNotFoundException("Customer", "Customer Id", customerId));

		List<Orders> orders = this.ordersRepo.findByCustomer(customerFound);
		return orders;
	}

	@Override
	public List<Orders> searchOrders(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
