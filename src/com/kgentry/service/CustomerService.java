package com.kgentry.service;

import com.kgentry.model.Customer;

import java.util.Collection;
import java.util.HashSet;

public class CustomerService {
    private static final CustomerService customerService = new CustomerService();
    private final Collection<Customer> customers = new HashSet<>();
    
    private CustomerService(){}
    
    public synchronized static CustomerService getInstance(){
        return customerService;
    }
    
    public void addCustomer(String firstName, String lastName, String email) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.add(customer);
    }

    public Customer getCustomer(String customerEmail) {
        Customer foundCustomer = null;
        for (Customer customer : customers) {
            if (customer.getEmail().equals(customerEmail)) {
                foundCustomer = customer;
            }
        }
        return foundCustomer;
    }

    public Collection<Customer> getAllCustomers() {
        return customers;
    }

}
