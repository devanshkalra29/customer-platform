package com.poc.customerplatform.service;

import com.poc.customerplatform.exception.CustomerNotFoundException;
import com.poc.customerplatform.exception.DuplicateEmailException;
import com.poc.customerplatform.model.CreateCustomerRequest;
import com.poc.customerplatform.model.Customer;
import com.poc.customerplatform.repository.CustomerRepository;
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
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerRepository customerRepository, MetricsService metricsService) {
        this.customerRepository = customerRepository;
        this.metricsService = metricsService;
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
        Customer newCustomer = createAndConvertToModel(customerRequest);

        metricsService.incrementCustomerCreatedCounter();

        return customerRepository.save(newCustomer);

    }

    public Customer updateCustomer(UUID id, CreateCustomerRequest customerRequest) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            logger.warn("Could not find customer with id {}, UPDATE FAILED", id);
            return new CustomerNotFoundException("Customer with id " + id + " was not found");
        });
        updateFields(customerRequest, customer);
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

    private Customer createAndConvertToModel(CreateCustomerRequest customerRequest) {
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

    private void updateFields(CreateCustomerRequest customerRequest, Customer customer) {
        customer.setPrefix(customerRequest.getPrefix());
        customer.setSuffix(customerRequest.getSuffix());
        customer.setMiddleName(customerRequest.getMiddleName());
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
    }
}
