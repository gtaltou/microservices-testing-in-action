package com.taltou.customerservice;


import com.taltou.customerservice.repository.CustomerRepository;
import com.taltou.customerservice.entities.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;

@SpringBootApplication
@Slf4j
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    @Profile("!test")
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
        log.info("================= Initialization ================");
        return args -> {
            List<Customer> customers = List.of(
                    Customer.builder()
                            .firstName("guy").lastName("taltou").email("gtaltou@gmail.com").build(),
                    Customer.builder()
                            .firstName("tom").lastName("boudin").email("tboudin@gmail.com").build(),
                    Customer.builder()
                            .firstName("ted").lastName("jonhson").email("tjonhson@gmail.com").build()
            );
            customerRepository.saveAll(customers);
        };
    }

}
