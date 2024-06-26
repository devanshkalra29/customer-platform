package com.poc.customerplatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID customerId;

    String prefix;
    String suffix;

    @NotBlank(message = "A first name is required")
    @Column(name = "first_name")
    String firstName;

    @Column(name="middle_name")
    String middleName;
    @NotBlank(message = "A last name is required")
    @Column(name = "last_name")
    String lastName;
    @Email(message = "A valid email is required")
    @NotBlank(message = "Email is required")
    String email;
    @Column(name = "phone_number")
    String phoneNumber;

    //needed for JPA
    public Customer() {

    }

    public Customer(String prefix, String suffix, String firstName, String middleName, String lastName, String phoneNumber, String email) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return customerId.equals(customer.customerId) && Objects.equals(prefix, customer.prefix) && Objects.equals(suffix, customer.suffix) && firstName.equals(customer.firstName) && Objects.equals(middleName, customer.middleName) && lastName.equals(customer.lastName) && email.equals(customer.email) && phoneNumber.equals(customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, prefix, suffix, firstName, middleName, lastName, email, phoneNumber);
    }
}
