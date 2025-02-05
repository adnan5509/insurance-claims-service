package com.aab.insurance.service;

import com.aab.insurance.dto.CustomerRequest;
import com.aab.insurance.dto.CustomerResponse;
import com.aab.insurance.exception.CustomerNotFoundException;
import com.aab.insurance.model.Customer;
import com.aab.insurance.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = new Customer(
                request.getFullName(),
                request.getDateOfBirth(),
                request.getInsuranceTypes()
        );
        Customer saved = customerRepository.save(customer);
        return new CustomerResponse(saved.getId(), saved.getFullName(), saved.getDateOfBirth(), saved.getInsuranceTypes());
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    public CustomerResponse getCustomerInfo(Long id) {
        Customer customer = getCustomerById(id);
        return new CustomerResponse(customer.getId(), customer.getFullName(), customer.getDateOfBirth(), customer.getInsuranceTypes());
    }
}
