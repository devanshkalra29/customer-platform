package com.poc.customerplatform

import com.poc.customerplatform.exception.CustomerNotFoundException
import com.poc.customerplatform.model.Customer
import com.poc.customerplatform.repository.CustomerRepository
import com.poc.customerplatform.service.CustomerService
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CustomerServiceTest extends Specification {

    CustomerRepository customerRepository = Mock(CustomerRepository);

    def customerService = new CustomerService(customerRepository)

    def "test find by customer email"() {
        given:
        def email = "johnny.appleseed@testemail.com"
        def customer = new Customer(prefix: null, suffix: null, firstName: "Johnny" , lastName: "Appleseed" , email: email)
        customerRepository.findByEmail(email) >> Optional.of(customer)

        when:
        def result = customerService.getCustomerByEmail(email)

        then:
        result == customer
    }

    def "test find customer by email when not found"() {
        given:
        def email = "johnny.appleseed@testemail.com"
        customerRepository.findByEmail(email) >> Optional.empty();

        when:
        def result = customerService.getCustomerByEmail(email)

        then:
        thrown(CustomerNotFoundException)
    }
}
