package com.poc.customerplatform.service;

import com.poc.customerplatform.exception.DuplicateEmailException;
import com.poc.customerplatform.model.CreateCustomerRequest;
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

    public Optional<Customer> getCustomerByEmail(String email){
        return customerRepository.findByEmail(email);
    }

    public Customer createCustomer(CreateCustomerRequest customerRequest){
        customerRepository.findByEmail(customerRequest.getEmail())
                .ifPresent(s -> {
                    throw new DuplicateEmailException("Email already exists");
                });
        Customer newCustomer = convertToModel(customerRequest);

        return customerRepository.save(newCustomer);

    }

    public Customer updateCustomer() {
        //TODO

        return new Customer();
    }

    private Customer convertToModel(CreateCustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setPrefix(customerRequest.getPrefix());
        customer.setSuffix(customerRequest.getSuffix());
        customer.setMiddleName(customerRequest.getMiddleName());
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());

        return customer;
    }
}
