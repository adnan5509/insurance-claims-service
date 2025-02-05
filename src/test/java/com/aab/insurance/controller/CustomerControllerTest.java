package com.aab.insurance.controller;

import com.aab.insurance.dto.CustomerRequest;
import com.aab.insurance.dto.CustomerResponse;
import com.aab.insurance.enums.InsuranceType;
import com.aab.insurance.exception.CustomerNotFoundException;
import com.aab.insurance.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testCreateCustomer_ReturnsCreatedCustomer() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setFullName("John Doe");
        request.setDateOfBirth(LocalDate.of(1990, 5, 15));
        request.setInsuranceTypes(Set.of(InsuranceType.HEALTH, InsuranceType.CAR));

        CustomerResponse response = new CustomerResponse(1L, "John Doe", LocalDate.of(1990, 5, 15), Set.of(InsuranceType.HEALTH, InsuranceType.CAR));
        when(customerService.createCustomer(ArgumentMatchers.any(CustomerRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content("{ \"fullName\": \"John Doe\", \"dateOfBirth\": \"1990-05-15\", \"insuranceTypes\": [\"HEALTH\", \"CAR\"] }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fullName", is("John Doe")))
                .andExpect(jsonPath("$.insuranceTypes", hasItems("HEALTH", "CAR")));
    }

    @Test
    void testGetCustomerInfo_ReturnsCustomer() throws Exception {
        CustomerResponse response = new CustomerResponse(1L, "John Doe", LocalDate.of(1990, 5, 15), Set.of(InsuranceType.HEALTH));
        when(customerService.getCustomerInfo(1L)).thenReturn(response);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fullName", is("John Doe")));
    }

    @Test
    void testGetCustomerInfo_ThrowsCustomerNotFoundException() throws Exception {
        when(customerService.getCustomerInfo(1L)).thenThrow(new CustomerNotFoundException("Customer not found"));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isNotFound());
    }
}
