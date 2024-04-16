package com.poc.customerplatform.service;

import com.poc.customerplatform.exception.CustomerNotFoundException;
import com.poc.customerplatform.exception.DuplicateEmailException;
import com.poc.customerplatform.model.CreateCustomerRequest;
import com.poc.customerplatform.model.Customer;
import com.poc.customerplatform.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByEmail(String email){
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException("Customer was not found"));

        return customer;
    }

    public Customer createCustomer(CreateCustomerRequest customerRequest){
        customerRepository.findByEmail(customerRequest.getEmail())
                .ifPresent(s -> {
                    throw new DuplicateEmailException("Email already exists under another customer, please provide a new one");
                });
        Customer newCustomer = createAndConvertToModel(customerRequest);

        return customerRepository.save(newCustomer);

    }

    public Customer updateCustomer(UUID id, CreateCustomerRequest customerRequest) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " was not found"));
        updateCustomer(customerRequest, customer);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " was not found"));

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

    private void updateCustomer(CreateCustomerRequest customerRequest, Customer customer) {
        customer.setPrefix(customerRequest.getPrefix());
        customer.setSuffix(customerRequest.getSuffix());
        customer.setMiddleName(customerRequest.getMiddleName());
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
    }
}
