package com.taltou.customerservice.repository;


import com.taltou.customerservice.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;
    @BeforeEach
    void setUp() {
        System.out.println("-----------------------------------------------");
        customerRepository.save(Customer.builder()
                .firstName("guy").lastName("taltou").email("gtaltou@gmail.com").build());
        customerRepository.save(Customer.builder()
                .firstName("tom").lastName("boudin").email("tboudin@gmail.com").build());
        customerRepository.save(Customer.builder()
                .firstName("ted").lastName("jonhson").email("tjonhson@gmail.com").build());
        System.out.println("-----------------------------------------------");
    }
    @Test
    void shouldFindCustomersByFirstName(){
        List<Customer> expected = List.of(
                Customer.builder().firstName("tom").lastName("boudin").email("tboudin@gmail.com").build(),
                Customer.builder().firstName("ted").lastName("jonhson").email("tjonhson@gmail.com").build()
        );
        List<Customer> result = customerRepository.findByFirstNameContainingIgnoreCase("t");
        //assertEquals(result.size(),2);
        assertThat(result.size()).isEqualTo(2);
        assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
    }


    @Test
    void shouldFindCustomersByEmail(){
        String givenEmail="gtaltou@gmail.com";
        Customer expected=Customer.builder().firstName("guy").lastName("taltou").email("gtaltou@gmail.com").build();
        Optional<Customer> result = customerRepository.findByEmail(givenEmail);
        assertThat(result).isPresent();
        assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result.get());
    }
    @Test
    void shouldNotFindCustomersByEmail(){
        String givenEmail="titi@gmail.com";
        Optional<Customer> result = customerRepository.findByEmail(givenEmail);
        assertThat(result).isEmpty();
    }
}