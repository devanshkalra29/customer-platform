package com.poc.customerplatform

import com.poc.customerplatform.controller.CustomerController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class LoadContextTest extends Specification {


    def "application context loads"() {
        expect: "customer-platform context loads without errorss"
        true
    }
}
