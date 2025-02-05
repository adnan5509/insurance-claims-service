package com.aab.insurance.service;

import com.aab.insurance.dto.ClaimRequest;
import com.aab.insurance.dto.ClaimStatusUpdateRequest;
import com.aab.insurance.exception.ClaimNotFoundException;
import com.aab.insurance.exception.InvalidClaimTypeException;
import com.aab.insurance.model.Customer;
import com.aab.insurance.model.InsuranceClaim;
import com.aab.insurance.repository.InsuranceClaimRepository;
import com.aab.insurance.enums.ClaimStatus;
import com.aab.insurance.enums.InsuranceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InsuranceClaimServiceTest {

    @Mock
    private InsuranceClaimRepository insuranceClaimRepository;

    @Mock
    private CustomerService customerService;

    private InsuranceClaimService insuranceClaimService;

    @BeforeEach
    void setUp() {
        insuranceClaimService = new InsuranceClaimService(insuranceClaimRepository, customerService);
    }

    @Test
    void testCreateClaim_ThrowsInvalidClaimTypeException_WhenInsuranceTypeNotCovered() {
        Customer customer = new Customer("John Doe", LocalDate.of(1990, 5, 15), Set.of(InsuranceType.HEALTH));
        ClaimRequest claimRequest = new ClaimRequest(InsuranceType.CAR, LocalDate.now(), 500.0);

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customer);

        assertThrows(InvalidClaimTypeException.class, () -> insuranceClaimService.createClaim(1L, claimRequest));
    }

    @Test
    void testCreateClaim_ReturnsInsuranceClaim_WhenValidClaim() {
        Customer customer = new Customer("John Doe", LocalDate.of(1990, 5, 15), Set.of(InsuranceType.CAR));
        ClaimRequest claimRequest = new ClaimRequest(InsuranceType.CAR, LocalDate.now(), 500.0);
        InsuranceClaim claim = new InsuranceClaim(InsuranceType.CAR, LocalDate.now(), ClaimStatus.OPEN, 500, customer);

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customer);
        Mockito.when(insuranceClaimRepository.save(ArgumentMatchers.any(InsuranceClaim.class))).thenReturn(claim);

        InsuranceClaim createdClaim = insuranceClaimService.createClaim(1L, claimRequest);
        assertEquals(InsuranceType.CAR, createdClaim.getClaimType());
        assertEquals(ClaimStatus.OPEN, createdClaim.getStatus());
    }

    @Test
    void testUpdateClaimStatus_ThrowsClaimNotFoundException_WhenClaimNotFound() {
        ClaimStatusUpdateRequest request = new ClaimStatusUpdateRequest();
        request.setStatus(ClaimStatus.CLOSED);

        Mockito.when(insuranceClaimRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClaimNotFoundException.class, () -> insuranceClaimService.updateClaimStatus(1L, request));
    }

    @Test
    void testUpdateClaimStatus_ReturnsUpdatedClaim_WhenStatusUpdated() {
        InsuranceClaim claim = new InsuranceClaim(InsuranceType.HEALTH, LocalDate.now(), ClaimStatus.OPEN, 500, new Customer());
        ClaimStatusUpdateRequest request = new ClaimStatusUpdateRequest();
        request.setStatus(ClaimStatus.CLOSED);

        Mockito.when(insuranceClaimRepository.findById(1L)).thenReturn(Optional.of(claim));
        Mockito.when(insuranceClaimRepository.save(ArgumentMatchers.any(InsuranceClaim.class))).thenReturn(claim);

        InsuranceClaim updatedClaim = insuranceClaimService.updateClaimStatus(1L, request);
        assertEquals(ClaimStatus.CLOSED, updatedClaim.getStatus());
    }

    @Test
    void testDeleteClaim_ThrowsClaimNotFoundException_WhenClaimNotFound() {
        Mockito.when(insuranceClaimRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClaimNotFoundException.class, () -> insuranceClaimService.deleteClaim(1L));
    }

    @Test
    void testDeleteClaim_SuccessfullyDeletesClaim() {
        InsuranceClaim claim = new InsuranceClaim(InsuranceType.HEALTH, LocalDate.now(), ClaimStatus.OPEN, 500, new Customer());

        Mockito.when(insuranceClaimRepository.findById(1L)).thenReturn(Optional.of(claim));
        Mockito.doNothing().when(insuranceClaimRepository).delete(ArgumentMatchers.any(InsuranceClaim.class));

        assertDoesNotThrow(() -> insuranceClaimService.deleteClaim(1L));
    }

    @Test
    void testGetClaimsForCustomer_ReturnsListOfClaims() {
        Customer customer = new Customer("John Doe", LocalDate.of(1990, 5, 15), Set.of(InsuranceType.CAR));
        InsuranceClaim claim = new InsuranceClaim(InsuranceType.CAR, LocalDate.now(), ClaimStatus.OPEN, 500, customer);

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customer);
        Mockito.when(insuranceClaimRepository.findByCustomer(customer)).thenReturn(List.of(claim));

        List<InsuranceClaim> claims = insuranceClaimService.getClaimsForCustomer(1L);
        assertEquals(1, claims.size());
        assertEquals(InsuranceType.CAR, claims.get(0).getClaimType());
    }
}
