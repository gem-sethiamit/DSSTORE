package com.example.DsStore.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DsStore.entities.BackOrders;
import com.example.DsStore.entities.Customer;
import com.example.DsStore.entities.Product;
import com.example.DsStore.exceptions.IdNotFoundException;
import com.example.DsStore.exceptions.ResourceNotFoundException;
import com.example.DsStore.repositories.BackOrdersRepo;
import com.example.DsStore.services.BackOrdersService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BackOrdersServiceImpl implements BackOrdersService {

	@Autowired
	private BackOrdersRepo backOrdersRepo;

	@Override
	public void createBackOrdersbyOrder(Customer customerFound, Product productFound, Integer orderQuantity) {
		BackOrders backOrders = new BackOrders();
		backOrders.setCustomer(customerFound);
		backOrders.setProduct(productFound);
		backOrders.setQuantity(orderQuantity);
		backOrders.setBackOrderTimeStamp(new Date());
		this.backOrdersRepo.save(backOrders);
		log.info("BackOrder  Created");
	}

	@Override
	public BackOrders getBackOrderById(Integer backOrderId) throws IdNotFoundException {
		BackOrders backOrdersFound = this.backOrdersRepo.findById(backOrderId)
				.orElseThrow(() -> new IdNotFoundException("backOrder", "baclOrder Id", backOrderId));
		log.info("BackOrder by id found");
		return backOrdersFound;
	}

	@Override
	public List<BackOrders> getAllbackOrders() throws ResourceNotFoundException {
		List<BackOrders> backOrders = this.backOrdersRepo.findAll();
		if (backOrders.isEmpty()) {
			log.error("Nothing found in orders ");
			throw new ResourceNotFoundException("No data present in database");
		}
		log.info("backOrder list found " + backOrders.size());
		return backOrders;
	}

	@Override
	public void delteOrder(Integer backOrderId) throws IdNotFoundException {
		BackOrders backOrderDelete = this.backOrdersRepo.findById(backOrderId)
				.orElseThrow(() -> new IdNotFoundException("backOrder", "baclOrder Id", backOrderId));

		this.backOrdersRepo.delete(backOrderDelete);
		log.info("BackOrder deleted with id " + backOrderId);

	}

}
