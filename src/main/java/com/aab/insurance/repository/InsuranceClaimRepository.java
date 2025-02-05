package com.aab.insurance.repository;

import com.aab.insurance.model.InsuranceClaim;
import com.aab.insurance.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaim, Long> {
    List<InsuranceClaim> findByCustomer(Customer customer);
}
