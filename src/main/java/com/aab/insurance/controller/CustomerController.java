package com.aab.insurance.controller;

import com.aab.insurance.dto.CustomerRequest;
import com.aab.insurance.dto.CustomerResponse;
import com.aab.insurance.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer API", description = "Operations related to customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            summary = "Create a new customer",
            description = "Creates a new customer with the provided details. The request body should include the full name, date of birth, and the set of insurance types."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerRequest request) {
        return customerService.createCustomer(request);
    }

    @Operation(
            summary = "Get customer information",
            description = "Retrieves the information for a customer identified by the given customerId."
    )
    @GetMapping("/{customerId}")
    public CustomerResponse getCustomerInfo(@PathVariable Long customerId) {
        return customerService.getCustomerInfo(customerId);
    }
}
