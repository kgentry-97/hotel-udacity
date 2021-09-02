package com.kgentry.service;

import com.kgentry.model.Customer;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerService {
    private static final CustomerService customerService = new CustomerService();
    private final ConcurrentHashMap<String, Customer> customers = new ConcurrentHashMap<>();
    
    private CustomerService(){}
    
    public synchronized static CustomerService getInstance(){
        return customerService;
    }
    
    public void addCustomer(String firstName, String lastName, String email) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer );
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

}
