package com.aab.insurance.service;

import com.aab.insurance.dto.CustomerRequest;
import com.aab.insurance.dto.CustomerResponse;
import com.aab.insurance.enums.InsuranceType;
import com.aab.insurance.exception.CustomerNotFoundException;
import com.aab.insurance.model.Customer;
import com.aab.insurance.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void testCreateCustomer() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setFullName("John Doe");
        customerRequest.setDateOfBirth(LocalDate.of(1990, 5, 15));
        customerRequest.setInsuranceTypes(Set.of(InsuranceType.HEALTH, InsuranceType.CAR));

        Customer customer = new Customer("John Doe", LocalDate.of(1990, 5, 15), Set.of(InsuranceType.HEALTH, InsuranceType.CAR));
        Mockito.when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(customer);

        CustomerResponse response = customerService.createCustomer(customerRequest);
        assertEquals("John Doe", response.getFullName());
    }

    @Test
    void testGetCustomerInfo_ThrowsException_WhenCustomerNotFound() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerInfo(1L));
    }

    @Test
    void testGetCustomerInfo_ReturnsCustomer() {
        Customer customer = new Customer("Jane Doe", LocalDate.of(1985, 2, 20), Set.of(InsuranceType.HEALTH));
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerResponse response = customerService.getCustomerInfo(1L);
        assertEquals("Jane Doe", response.getFullName());
    }
}
