package com.poc.customerplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
public class CustomerPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerPlatformApplication.class, args);
	}

}
