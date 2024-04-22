package com.poc.customerplatform.service;

import com.poc.customerplatform.exception.CustomerNotFoundException;
import com.poc.customerplatform.exception.DuplicateEmailException;
import com.poc.customerplatform.model.CreateCustomerRequest;
import com.poc.customerplatform.model.Customer;
import com.poc.customerplatform.repository.CustomerRepository;
import com.poc.customerplatform.utils.RequestToCustomerConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class CustomerService {

    MetricsService metricsService;
    private final CustomerRepository customerRepository;
    RequestToCustomerConverter requestToCustomerConverter;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerRepository customerRepository, MetricsService metricsService, RequestToCustomerConverter requestToCustomerConverter) {
        this.customerRepository = customerRepository;
        this.metricsService = metricsService;
        this.requestToCustomerConverter = requestToCustomerConverter;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByEmail(String email){
        return customerRepository.findByEmail(email).orElseThrow(() -> {
            logger.warn("Search for customer with email {} was not found", email);
            return new CustomerNotFoundException("Customer with email " + email + " was not found");
        });
    }

    public Customer getCustomerById(UUID id) {
        return customerRepository.findById(id).orElseThrow(() -> {
            logger.warn("search for Customer with id {} was not found", id);
            return new CustomerNotFoundException("Customer with id " + id + "not found");
        });
    }

    public Customer createCustomer(CreateCustomerRequest customerRequest){
        customerRepository.findByEmail(customerRequest.getEmail())
                .ifPresent(s -> {
                    logger.warn("Creation of customer with email {} already exists, CREATE FAILED", customerRequest.getEmail());
                    throw new DuplicateEmailException("Email " + customerRequest.getEmail() +" already exists under another customer, please provide a new one");
                });
        Customer newCustomer = requestToCustomerConverter.createAndConvertToModel(customerRequest);

        metricsService.incrementCustomerCreatedCounter();

        return customerRepository.save(newCustomer);

    }

    public Customer updateCustomer(UUID id, CreateCustomerRequest customerRequest) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            logger.warn("Could not find customer with id {}, UPDATE FAILED", id);
            return new CustomerNotFoundException("Customer with id " + id + " was not found");
        });
       requestToCustomerConverter.updateFields(customerRequest, customer);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->{
            logger.warn("Could not find customer with id {}, DELETE FAILED", id);
            return new CustomerNotFoundException("Customer with id " + id + " was not found");
        });
        metricsService.incrementCustomerDeletedCounter();
        customerRepository.delete(customer);
    }
}
