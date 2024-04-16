package com.poc.customerplatform.controller;

import com.poc.customerplatform.model.CreateCustomerRequest;
import com.poc.customerplatform.model.Customer;
import com.poc.customerplatform.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        return customerService.getCustomerByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CreateCustomerRequest customerRequest) {
        Customer newCustomer = customerService.createCustomer(customerRequest);

        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

//    @PutMapping(/{"id"})
}
