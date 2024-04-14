package com.poc.customerplatform.repository;

import com.poc.customerplatform.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    public Optional<Customer> findByEmail(String email);
}
