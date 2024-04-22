package com.poc.customerplatform.utils;

import com.poc.customerplatform.model.CreateCustomerRequest;
import com.poc.customerplatform.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class RequestToCustomerConverter {

    public Customer createAndConvertToModel(CreateCustomerRequest customerRequest) {
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

    public void updateFields(CreateCustomerRequest customerRequest, Customer customer) {
        customer.setPrefix(customerRequest.getPrefix());
        customer.setSuffix(customerRequest.getSuffix());
        customer.setMiddleName(customerRequest.getMiddleName());
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
    }
}
