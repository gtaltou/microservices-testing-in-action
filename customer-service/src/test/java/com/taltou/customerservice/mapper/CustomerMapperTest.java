package com.taltou.customerservice.mapper;

import com.taltou.customerservice.dto.CustomerDTO;
import com.taltou.customerservice.entities.Customer;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.List;

class CustomerMapperTest {
    private final CustomerMapper customerMapper = new CustomerMapper();

    @Test
    void shouldMapCustomerToCustomerDTO() {
        Customer givenCustomer = Customer.builder()
                .id(1L).firstName("guy").lastName("taltou").email("gtaltou@gmail.com")
                .build();
        CustomerDTO expected = CustomerDTO.builder()
                .id(1L).firstName("guy").lastName("taltou").email("gtaltou@gmail.com")
                .build();

        CustomerDTO result = customerMapper.fromCustomer(givenCustomer);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void shouldMapCustomerDTOtoCustomer() {
        CustomerDTO givenCustomerDTO = CustomerDTO.builder()
                .id(1L).firstName("guy").lastName("taltou").email("gtaltou@gmail.com")
                .build();
        Customer expected = Customer.builder()
                .id(1L).firstName("guy").lastName("taltou").email("gtaltou@gmail.com")
                .build();
        Customer result = customerMapper.fromCustomerDTO(givenCustomerDTO);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void shouldMapListOfCustomersToListCustomerDTOs() {
        List<Customer> givenCustomers=List.of(
               Customer.builder().id(1L).firstName("guy").lastName("taltou").email("gtaltou@gmail.com").build() ,
                Customer.builder().id(2L).firstName("tom").lastName("boudin").email("tboudin@gmail.com").build()
        );
        List<CustomerDTO> expected=List.of(
                CustomerDTO.builder().id(1L).firstName("guy").lastName("taltou").email("gtaltou@gmail.com").build() ,
                CustomerDTO.builder().id(2L).firstName("tom").lastName("boudin").email("tboudin@gmail.com").build()
        );
        List<CustomerDTO> result = customerMapper.fromListCustomers(givenCustomers);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }
    @Test
    void shouldNotMapNullCustomerToCustomerDTO() {
        AssertionsForClassTypes.assertThatThrownBy(
                ()->customerMapper.fromCustomer(null)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}