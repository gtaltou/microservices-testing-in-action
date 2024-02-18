package com.taltou.customerservice.service;


import com.taltou.customerservice.dto.CustomerDTO;
import com.taltou.customerservice.entities.Customer;
import com.taltou.customerservice.exceptions.CustomerNotFoundException;
import com.taltou.customerservice.exceptions.EmailAlreadyExistException;
import com.taltou.customerservice.mapper.CustomerMapper;
import com.taltou.customerservice.repository.CustomerRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @InjectMocks
    private CustomerServiceImpl underTest;
    @Test
    void shouldSaveNewCustomer() {
        CustomerDTO customerDTO= CustomerDTO.builder()
                .firstName("magali").lastName("bouchar").email("mbouchar@gmail.com").build();
        Customer customer= Customer.builder()
                .firstName("magali").lastName("bouchar").email("mbouchar@gmail.com").build();
        Customer savedCustomer= Customer.builder()
                .id(1L).firstName("magali").lastName("bouchar").email("mbouchar@gmail.com").build();
        CustomerDTO expected= CustomerDTO.builder()
                .id(1L).firstName("magali").lastName("bouchar").email("mbouchar@gmail.com").build();
        Mockito.when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(Optional.empty());
        Mockito.when(customerMapper.fromCustomerDTO(customerDTO)).thenReturn(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(savedCustomer);
        Mockito.when(customerMapper.fromCustomer(savedCustomer)).thenReturn(expected);
        CustomerDTO result = underTest.saveNewCustomer(customerDTO);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void shouldNotSaveNewCustomerWhenEmailExist() {
        CustomerDTO customerDTO= CustomerDTO.builder()
                .firstName("magali").lastName("bouchar").email("xxxxx@gmail.com").build();
        Customer customer= Customer.builder()
                .id(5L).firstName("magali").lastName("bouchar").email("xxxxx@gmail.com").build();
        Mockito.when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(Optional.of(customer));
        AssertionsForClassTypes.assertThatThrownBy(()->underTest.saveNewCustomer(customerDTO))
                        .isInstanceOf(EmailAlreadyExistException.class);
    }

    @Test
    void shouldGetAllCustomers() {
        List<Customer> customers = List.of(
                Customer.builder().firstName("guy").lastName("taltou").email("gtaltou@gmail.com").build(),
                Customer.builder().firstName("ted").lastName("jonhson").email("tjonhson@gmail.com").build()
        );
        List<CustomerDTO> expected = List.of(
                CustomerDTO.builder().firstName("guy").lastName("taltou").email("gtaltou@gmail.com").build(),
                CustomerDTO.builder().firstName("ted").lastName("jonhson").email("tjonhson@gmail.com").build()
        );
        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        Mockito.when(customerMapper.fromListCustomers(customers)).thenReturn(expected);
        List<CustomerDTO> result = underTest.getAllCustomers();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void shouldFindCustomerById() {
        Long customerId = 1L;
        Customer customer=Customer.builder().id(1L).firstName("guy").lastName("taltou").email("gtaltou@gmail.com").build();
        CustomerDTO expected=CustomerDTO.builder().id(1L).firstName("guy").lastName("taltou").email("gtaltou@gmail.com").build();
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Mockito.when(customerMapper.fromCustomer(customer)).thenReturn(expected);
        CustomerDTO result = underTest.findCustomerById(customerId);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }
    @Test
    void shouldNotFindCustomerById() {
        Long customerId = 10L;
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        AssertionsForClassTypes.assertThatThrownBy(()->underTest.findCustomerById(customerId))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(null);
    }

    @Test
    void shouldSearchCustomers() {
        String keyword="m";
        List<Customer> customers = List.of(
                Customer.builder().firstName("guy").lastName("taltou").email("gtaltou@gmail.com").build(),
                Customer.builder().firstName("ted").lastName("jonhson").email("tjonhson@gmail.com").build()
        );
        List<CustomerDTO> expected = List.of(
                CustomerDTO.builder().firstName("guy").lastName("taltou").email("gtaltou@gmail.com").build(),
                CustomerDTO.builder().firstName("ted").lastName("jonhson").email("tjonhson@gmail.com").build()
        );
        Mockito.when(customerRepository.findByFirstNameContainingIgnoreCase(keyword)).thenReturn(customers);
        Mockito.when(customerMapper.fromListCustomers(customers)).thenReturn(expected);
        List<CustomerDTO> result = underTest.searchCustomers(keyword);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void updateCustomer() {
        Long customerId= 6L;
        CustomerDTO customerDTO= CustomerDTO.builder()
                .id(6L).firstName("guy").lastName("taltou").email("taltou@gmail.com").build();
        Customer customer= Customer.builder()
                .id(6L).firstName("guy").lastName("taltou").email("taltou@gmail.com").build();
        Customer updatedCustomer= Customer.builder()
                .id(6L).firstName("guy").lastName("taltou").email("taltou@gmail.com").build();
        CustomerDTO expected= CustomerDTO.builder()
                .id(6L).firstName("guy").lastName("taltou").email("taltou@gmail.com").build();
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Mockito.when(customerMapper.fromCustomerDTO(customerDTO)).thenReturn(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(updatedCustomer);
        Mockito.when(customerMapper.fromCustomer(updatedCustomer)).thenReturn(expected);
        CustomerDTO result = underTest.updateCustomer(customerId,customerDTO);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void shouldDeleteCustomer() {
        Long customerId =1L;
        Customer customer= Customer.builder()
                .id(6L).firstName("guy").lastName("taltou").email("taltou@gmail.com").build();
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        underTest.deleteCustomer(customerId);
        Mockito.verify(customerRepository).deleteById(customerId);
    }
    @Test
    void shouldNotDeleteCustomerIfNotExist() {
        Long customerId =10L;
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        AssertionsForClassTypes.assertThatThrownBy(()->underTest.deleteCustomer(customerId))
                        .isInstanceOf(CustomerNotFoundException.class);
    }
}