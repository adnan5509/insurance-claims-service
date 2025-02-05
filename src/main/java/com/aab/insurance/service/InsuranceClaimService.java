package com.aab.insurance.service;

import com.aab.insurance.dto.ClaimRequest;
import com.aab.insurance.dto.ClaimStatusUpdateRequest;
import com.aab.insurance.exception.ClaimNotFoundException;
import com.aab.insurance.exception.InvalidClaimTypeException;
import com.aab.insurance.enums.ClaimStatus;
import com.aab.insurance.model.Customer;
import com.aab.insurance.model.InsuranceClaim;
import com.aab.insurance.enums.InsuranceType;
import com.aab.insurance.repository.InsuranceClaimRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class InsuranceClaimService {

    private final InsuranceClaimRepository claimRepository;
    private final CustomerService customerService;

    public InsuranceClaimService(InsuranceClaimRepository claimRepository, CustomerService customerService) {
        this.claimRepository = claimRepository;
        this.customerService = customerService;
    }

    public InsuranceClaim createClaim(Long customerId, ClaimRequest request) {

        if (request.getClaimType() == null) {
            throw new InvalidClaimTypeException("Claim type must be provided.");
        }

        Customer customer = customerService.getCustomerById(customerId);

        // Check if the customer covers this type of insurance
        if (!isInsuranceCovered(customer.getInsuranceTypes(), request.getClaimType())) {
            throw new InvalidClaimTypeException("Customer does not have the insurance cover for " + request.getClaimType());
        }

        InsuranceClaim claim = new InsuranceClaim(
                request.getClaimType(),
                request.getDate(),
                ClaimStatus.OPEN,
                request.getCost(),
                customer
        );
        return claimRepository.save(claim);
    }

    public InsuranceClaim updateClaimStatus(Long claimId, ClaimStatusUpdateRequest request) {
        InsuranceClaim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found"));
        claim.setStatus(request.getStatus());
        return claimRepository.save(claim);
    }

    public void deleteClaim(Long claimId) {
        InsuranceClaim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found"));
        claimRepository.delete(claim);
    }

    public List<InsuranceClaim> getClaimsForCustomer(Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return claimRepository.findByCustomer(customer);
    }

    private boolean isInsuranceCovered(Set<InsuranceType> insuranceTypesString, InsuranceType claimType) {
        return insuranceTypesString.contains(claimType);
    }
}
