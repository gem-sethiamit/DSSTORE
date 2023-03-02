package com.example.DsStore.services;

import com.example.DsStore.entities.Customer;
import com.example.DsStore.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer,Integer customerId);
    Customer getCustomerById(Integer customerId);
    List<Customer> getAllCustomers();
    void deleteCustomer(Integer customerId);

}
