package com.poc.customerplatform

import com.poc.customerplatform.exception.CustomerNotFoundException
import com.poc.customerplatform.exception.DuplicateEmailException
import com.poc.customerplatform.model.CreateCustomerRequest
import com.poc.customerplatform.model.Customer
import com.poc.customerplatform.repository.CustomerRepository
import com.poc.customerplatform.service.CustomerService
import com.poc.customerplatform.service.MetricsService
import com.poc.customerplatform.utils.RequestToCustomerConverter
import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CustomerServiceSpec extends Specification {


    CustomerRepository customerRepository = Mock(CustomerRepository);

    MetricsService metricsService = Mock(MetricsService)

    RequestToCustomerConverter requestConverter = new RequestToCustomerConverter();

    def customerService = new CustomerService(customerRepository, metricsService, requestConverter)

    def setup() {
        def json = new File("src/test/resources/mockCustomers.json").text
        def customers = new JsonSlurper().parseText(json)
        customers.each {customer ->

            Customer cust = new Customer(
                customerId: UUID.fromString(customer.id),
                prefix: customer.prefix,
                suffix: customer.suffix,
                firstName: customer.firstName,
                middleName: customer.middleName,
                lastName: customer.lastName,
                email: customer.email,
                phoneNumber: customer.phoneNumber
            )
            customerRepository.findByEmail(cust.getEmail()) >>Optional.of(cust)
            customerRepository.findById(cust.getCustomerId()) >> Optional.of(cust)
        }
        customerRepository.findByEmail(_) >> Optional.empty()
        customerRepository.findById(_) >> Optional.empty()
    }

    def "test for find by customer email"() {
        expect:
        def email = "jane.johnson@test.com"
        def result = customerService.getCustomerByEmail(email)

        with(result) {
           firstName == "Jane"
           lastName == "Johnson"
        }
    }

    def "test for find customer by email when not found"() {
        given:
        def email = "emaildoesntexists@test.com"

        when:
        def result = customerService.getCustomerByEmail(email)

        then:
        thrown(CustomerNotFoundException)
    }

    def "test for find by customer id"() {
     expect:
     def uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
     def result = customerService.getCustomerById(uuid)

     with(result) {
         firstName == "Johnny"
         lastName == "Appleseed"
     }
    }

    def "test for find customer by id when not found"() {
        given:
        def uuid = UUID.fromString("123e4567-e89b-13d3-a457-426614174000")

        when:
        def result = customerService.getCustomerById(uuid)

        then:
        thrown(CustomerNotFoundException)
    }

    def "test for createCustomer successful (unique email)"(){
        given:
        def customer = new Customer(null, null, "robot", null, "chicken", "robotchicken@test.com", "09876543210")
        def customerRequest = new CreateCustomerRequest(null, null,"robot", null,"chicken","robotchicken@test.com","09876543210")
        customerRepository.save(_) >> customer

        when:
        def result = customerService.createCustomer(customerRequest)

        then:
        1 * metricsService.incrementCustomerCreatedCounter()
        result == customer
    }

    def "test for emailExists when creating customer (non-unique email)"() {
        given:
        def customerRequest = new CreateCustomerRequest(null, null,"robot", null,"chicken","jane.johnson@test.com","09876543210")

        when:
        def result = customerService.createCustomer(customerRequest)

        then:
        thrown(DuplicateEmailException)
    }

    def "test for updating a customer"() {
        given:
        def id = UUID.fromString("123e4567-e89b-12d3-a456-426614174001")
        def customer = customerService.getCustomerById(id)
        def customerRequest = new CreateCustomerRequest(null, null,"robot", null,"chicken","jane.johnson@test.com","09876543210")

        when:
        def result = customerService.updateCustomer(id,customerRequest)

        then:
        assert customer.getFirstName().equals("robot")
        assert customer.getLastName().equals("chicken")
        assert customer.getPhoneNumber().equals("09876543210")
    }

    def "test for deleting a customer"() {
        given:
        def id = UUID.fromString("123e4567-e89b-12d3-a456-426614174001")

        when:
        customerService.deleteCustomer(id)

        then:
        1 * metricsService.incrementCustomerDeletedCounter()
    }
}
