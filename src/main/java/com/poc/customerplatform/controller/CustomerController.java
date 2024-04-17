package com.poc.customerplatform.controller;

import com.poc.customerplatform.model.CreateCustomerRequest;
import com.poc.customerplatform.model.Customer;
import com.poc.customerplatform.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        logger.info("retrieval of all customers was successful");
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
       Customer customer = customerService.getCustomerByEmail(email);
        logger.info("Found customer with email {}", email);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id) {
        Customer customer = customerService.getCustomerById(id);
        logger.info("Found customer with id {}", id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/new")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CreateCustomerRequest customerRequest) {
        logger.info("Received request to create new customer: {}", customerRequest);
        Customer newCustomer = customerService.createCustomer(customerRequest);

        logger.info("Creation of new customer successful {}", newCustomer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id, @Valid @RequestBody CreateCustomerRequest customerRequest) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerRequest);
        logger.info("updated customer with id {} as {}", id, updatedCustomer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        logger.info("deleted customer {}", id);
       return ResponseEntity.noContent().build();
    }
}
