package com.example.DsStore.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.entities.Orders;
import com.example.DsStore.entities.Product;
import com.example.DsStore.exceptions.ApiResponse;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.repositories.CustomerRepo;
import com.example.DsStore.repositories.OrdersRepo;
import com.example.DsStore.repositories.ProductRepo;
import com.example.DsStore.services.OrdersService;

import jakarta.persistence.criteria.Order;
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

	@Autowired
	private ProductServiceImpl productServiceImpl;

	/**
	 * This method is used to create Order in the database.
	 *
	 * @return savedOrder
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Orders createOrder(Orders orders, Integer customerId, Integer productId)
			throws IdNotFoundException, ResourceNotFoundException {

		Customer customerFound = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new IdNotFoundException("Customer", "Customer Id", customerId));

		Product productFound = this.productRepo.findById(productId)
				.orElseThrow(() -> new IdNotFoundException("Product", "Product Id", productId));

		int orderQuantity = orders.getQuantity();
		int productCount = productFound.getCount();
		if (productCount >= orderQuantity) {
			productCount = productCount - orderQuantity;
			productFound.setCount(productCount);
			productServiceImpl.updateProduct(productFound, productId);
		} else {
			throw new ResourceNotFoundException("Product count is less we addedd order in backlog");
		}

		orders.setOrderTimeStamp(new Date());
		orders.setCustomer(customerFound);
		orders.setProduct(productFound);

		Orders savedOrder = this.ordersRepo.save(orders);

		return savedOrder;
	}

	/**
	 * This method is used to update Order in the database.
	 *
	 * @return updatedOrder
	 * @throws IdNotFoundException No orderId found
	 */
	@Override
	public Orders updateOrder(Orders changeOrder, Integer customerId, Integer productId, Integer orderId)
			throws IdNotFoundException {
		Orders oldOrder = this.ordersRepo.findById(orderId)
				.orElseThrow(() -> new IdNotFoundException("Order", "Order Id", orderId));
		Customer customerFound = this.customerRepo.findById(customerId)
				.orElseThrow(() -> new IdNotFoundException("Customer", "Customer Id", customerId));

		Product productFound = this.productRepo.findById(productId)
				.orElseThrow(() -> new IdNotFoundException("Product", "Product Id", productId));

		oldOrder.setQuantity(changeOrder.getQuantity());
		oldOrder.setCustomer(customerFound);
		oldOrder.setProduct(productFound);
		oldOrder.setOrderTimeStamp(new Date());

		Orders updatedOrder = this.ordersRepo.save(oldOrder);
		log.info("Order Updated");
		return updatedOrder;
	}

	/**
	 * This method is used to Get specific Order from database based on id.
	 *
	 * @return orderFound
	 * @throws IdNotFoundException No orderId found
	 */
	@Override
	public Orders getOrderById(Integer orderId) throws IdNotFoundException {
		Orders orderFound = this.ordersRepo.findById(orderId)
				.orElseThrow(() -> new IdNotFoundException("Order", "Order Id", orderId));
		log.info("Order by id found");
		return orderFound;
	}

	/**
	 * This method is used to Get All the Orders from database.
	 *
	 * @return customers
	 * @throws ResourceNotFoundException if No data found in database
	 */
	@Override
	public List<Orders> getAllOrders() throws ResourceNotFoundException {
		List<Orders> orders = this.ordersRepo.findAll();
		log.info("Order list found size " + orders.size());
		return orders;
	}

	/**
	 * This method is used to delete the specific order from the database on order
	 * id.
	 *
	 * @throws IdNotFoundException No orderId found
	 */
	@Override
	public void delteOrder(Integer orderId) throws IdNotFoundException {
		Orders orderDelete = this.ordersRepo.findById(orderId)
				.orElseThrow(() -> new IdNotFoundException("Order", "Order Id", orderId));

		this.ordersRepo.delete(orderDelete);
		log.info("Order deleted with id " + orderId);

	}

}
