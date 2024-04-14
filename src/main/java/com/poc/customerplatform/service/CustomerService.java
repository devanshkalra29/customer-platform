package com.poc.customerplatform.service;

import com.poc.customerplatform.model.Customer;
import com.poc.customerplatform.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
//
    public Optional<Customer> getCustomerByEmail(String email){
        return customerRepository.findByEmail(email);
    }
//
//    public void createCustomer(){}
//
//    public Optional<Customer> deleteCustomer() {
//
//    }
}
