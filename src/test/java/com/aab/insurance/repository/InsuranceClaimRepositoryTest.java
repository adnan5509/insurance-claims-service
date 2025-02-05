package com.aab.insurance.repository;

import com.aab.insurance.model.Customer;
import com.aab.insurance.model.InsuranceClaim;
import com.aab.insurance.enums.ClaimStatus;
import com.aab.insurance.enums.InsuranceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DataJpaTest
public class InsuranceClaimRepositoryTest {

    @MockitoBean
    private InsuranceClaimRepository insuranceClaimRepository;

    private InsuranceClaim insuranceClaim;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("John Doe", LocalDate.of(1985, 2, 20), Set.of(InsuranceType.CAR, InsuranceType.HEALTH));
        insuranceClaim = new InsuranceClaim(InsuranceType.CAR, LocalDate.now(), ClaimStatus.OPEN, 1000, customer);
    }

    @Test
    void testFindById_InsuranceClaimExists() {
        when(insuranceClaimRepository.findById(1L)).thenReturn(Optional.of(insuranceClaim));

        Optional<InsuranceClaim> foundClaim = insuranceClaimRepository.findById(1L);
        assertThat(foundClaim).isPresent();
        assertThat(foundClaim.get().getClaimType()).isEqualTo(InsuranceType.CAR);
    }

    @Test
    void testFindById_InsuranceClaimNotFound() {
        when(insuranceClaimRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<InsuranceClaim> foundClaim = insuranceClaimRepository.findById(1L);
        assertThat(foundClaim).isNotPresent();
    }

    @Test
    void testFindByCustomer() {
        when(insuranceClaimRepository.findByCustomer(customer)).thenReturn(List.of(insuranceClaim));

        List<InsuranceClaim> claims = insuranceClaimRepository.findByCustomer(customer);
        assertThat(claims).hasSize(1);
        assertThat(claims.get(0).getClaimType()).isEqualTo(InsuranceType.CAR);
    }

    @Test
    void testSaveInsuranceClaim() {
        when(insuranceClaimRepository.save(insuranceClaim)).thenReturn(insuranceClaim);

        InsuranceClaim savedClaim = insuranceClaimRepository.save(insuranceClaim);
        assertThat(savedClaim.getClaimType()).isEqualTo(InsuranceType.CAR);
        assertThat(savedClaim.getStatus()).isEqualTo(ClaimStatus.OPEN);
    }

    @Test
    void testDeleteInsuranceClaim() {
        insuranceClaimRepository.delete(insuranceClaim);
        verify(insuranceClaimRepository, times(1)).delete(insuranceClaim);
    }
}
