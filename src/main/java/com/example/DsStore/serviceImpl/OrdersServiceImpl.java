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

	@Autowired
	private BackOrdersServiceImpl backOrdersServiceImpl;

	/**
	 * This method is used to create Order in the database.
	 * 
	 * @param orders     - Orders Details
	 * @param customerId id of Customer
	 * @param productId  id of Product
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
		Boolean Availabity = productFound.isAvalibity();

		if (productCount >= orderQuantity && Availabity == true) {
			productCount = productCount - orderQuantity;
			productFound.setCount(productCount);
			productServiceImpl.updateProduct(productFound, productId);

		} else {
			backOrdersServiceImpl.createBackOrdersbyOrder(customerFound, productFound, orderQuantity);
			throw new ResourceNotFoundException("Product count is less We addedd order in backlog");
		}

		orders.setOrderTimeStamp(new Date());
		orders.setCustomer(customerFound);
		orders.setProduct(productFound);

		Orders savedOrder = this.ordersRepo.save(orders);
		int orderPrice = orders.getQuantity() * productFound.getPrice();
		if (orderPrice >= 1000) {
			orderPrice = orderPrice - 100;
			log.info("Order price is is above 1000 you got 100 rs Flat off " + orderPrice);
		} else {
			log.info("Order Created with price " + orderPrice);
		}
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
	 * @return orders
	 * @throws ResourceNotFoundException if No data found in database
	 */
	@Override
	public List<Orders> getAllOrders() throws ResourceNotFoundException {
		List<Orders> orders = this.ordersRepo.findAll();
		if (orders.isEmpty()) {
			log.error("Nothing found in orders ");
			throw new ResourceNotFoundException("No data present in database");
		}
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
	public void deleteOrder(Integer orderId) throws IdNotFoundException {
		Orders orderDelete = this.ordersRepo.findById(orderId)
				.orElseThrow(() -> new IdNotFoundException("Order", "Order Id", orderId));

		this.ordersRepo.delete(orderDelete);
		log.info("Order deleted with id " + orderId);

	}

}
