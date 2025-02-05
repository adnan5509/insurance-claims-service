package com.aab.insurance.controller;

import com.aab.insurance.dto.ClaimRequest;
import com.aab.insurance.dto.ClaimStatusUpdateRequest;
import com.aab.insurance.enums.ClaimStatus;
import com.aab.insurance.enums.InsuranceType;
import com.aab.insurance.model.InsuranceClaim;
import com.aab.insurance.service.InsuranceClaimService;
import com.aab.insurance.exception.ClaimNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class ClaimControllerTest {

    @InjectMocks
    private ClaimController claimController;

    @Mock
    private InsuranceClaimService claimService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(claimController).build();
    }

    @Test
    void testCreateClaim() throws Exception {
        ClaimRequest request = new ClaimRequest();
        request.setCost(1000.0);
        request.setClaimType(InsuranceType.HEALTH);

        InsuranceClaim mockClaim = new InsuranceClaim();
        mockClaim.setId(1L);
        mockClaim.setCost(1000.0);
        mockClaim.setClaimType(InsuranceType.HEALTH);

        when(claimService.createClaim(eq(1L), org.mockito.ArgumentMatchers.any(ClaimRequest.class))).thenReturn(mockClaim);

        mockMvc.perform(post("/api/customers/{customerId}/claims", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cost\": 1000.0, \"claimType\": \"HEALTH\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.cost", is(1000.0)))
                .andExpect(jsonPath("$.claimType", is("HEALTH")));
    }

    @Test
    void testUpdateClaimStatus() throws Exception {
        ClaimStatusUpdateRequest request = new ClaimStatusUpdateRequest();
        request.setStatus(ClaimStatus.CLOSED);

        InsuranceClaim mockClaim = new InsuranceClaim();
        mockClaim.setId(1L);
        mockClaim.setCost(1000.0);
        mockClaim.setClaimType(InsuranceType.HEALTH);
        mockClaim.setStatus(ClaimStatus.CLOSED);

        when(claimService.updateClaimStatus(eq(1L), org.mockito.ArgumentMatchers.any(ClaimStatusUpdateRequest.class))).thenReturn(mockClaim);

        mockMvc.perform(put("/api/claims/{claimId}/status", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"CLOSED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("CLOSED")));
    }

    @Test
    void testDeleteClaim() throws Exception {
        doNothing().when(claimService).deleteClaim(1L);

        mockMvc.perform(delete("/api/claims/{claimId}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetClaimsForCustomer() throws Exception {
        InsuranceClaim claim1 = new InsuranceClaim();
        claim1.setId(1L);
        claim1.setCost(1000.0);
        claim1.setClaimType(InsuranceType.HEALTH);

        InsuranceClaim claim2 = new InsuranceClaim();
        claim2.setId(2L);
        claim2.setCost(2000.0);
        claim2.setClaimType(InsuranceType.CAR);

        when(claimService.getClaimsForCustomer(1L)).thenReturn(List.of(claim1, claim2));

        mockMvc.perform(get("/api/customers/{customerId}/claims", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void testCreateClaim_InvalidRequest() throws Exception {
        mockMvc.perform(post("/api/customers/{customerId}/claims", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateClaimStatus_ClaimNotFound() throws Exception {
        when(claimService.updateClaimStatus(eq(999L), org.mockito.ArgumentMatchers.any(ClaimStatusUpdateRequest.class)))
                .thenThrow(new ClaimNotFoundException("Claim with ID 999 not found"));

        mockMvc.perform(put("/api/claims/{claimId}/status", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"CLOSED\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteClaim_ClaimNotFound() throws Exception {
        doThrow(new ClaimNotFoundException("Claim with ID 999 not found"))
                .when(claimService).deleteClaim(999L);

        mockMvc.perform(delete("/api/claims/{claimId}", 999L))
                .andExpect(status().isNotFound());
    }
}
