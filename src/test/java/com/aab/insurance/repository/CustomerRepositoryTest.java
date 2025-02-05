package com.aab.insurance.repository;

import com.aab.insurance.model.Customer;
import com.aab.insurance.enums.InsuranceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
public class CustomerRepositoryTest {

    @MockitoBean
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("John Doe", LocalDate.of(1985, 2, 20), Set.of(InsuranceType.CAR, InsuranceType.HEALTH));
    }

    @Test
    void testFindById_CustomerExists() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> foundCustomer = customerRepository.findById(1L);
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getFullName()).isEqualTo("John Doe");
    }

    @Test
    void testFindById_CustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Customer> foundCustomer = customerRepository.findById(1L);
        assertThat(foundCustomer).isNotPresent();
    }

    @Test
    void testSaveCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerRepository.save(customer);
        assertThat(savedCustomer.getFullName()).isEqualTo("John Doe");
    }
}
