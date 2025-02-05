package com.aab.insurance.controller;

import com.aab.insurance.dto.ClaimRequest;
import com.aab.insurance.dto.ClaimStatusUpdateRequest;
import com.aab.insurance.model.InsuranceClaim;
import com.aab.insurance.service.InsuranceClaimService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Claim API", description = "Operations related to insurance claims")
public class ClaimController {

    private final InsuranceClaimService claimService;

    public ClaimController(InsuranceClaimService claimService) {
        this.claimService = claimService;
    }

    @Operation(
            summary = "Create a new insurance claim",
            description = "Creates a new insurance claim for the specified customer. The customerId is passed in the URL and the claim details in the request body."
    )
    @PostMapping("/customers/{customerId}/claims")
    @ResponseStatus(HttpStatus.CREATED)
    public InsuranceClaim createClaim(
            @PathVariable Long customerId,
            @Valid @RequestBody ClaimRequest request) {
        return claimService.createClaim(customerId, request);
    }

    @Operation(
            summary = "Update claim status",
            description = "Updates the status of an existing insurance claim identified by claimId."
    )
    @PutMapping("/claims/{claimId}/status")
    public InsuranceClaim updateClaimStatus(
            @PathVariable Long claimId,
            @Valid @RequestBody ClaimStatusUpdateRequest request) {
        return claimService.updateClaimStatus(claimId, request);
    }

    @Operation(
            summary = "Delete an insurance claim",
            description = "Deletes the insurance claim identified by the provided claimId."
    )

    @DeleteMapping("/claims/{claimId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClaim(@PathVariable Long claimId) {
        claimService.deleteClaim(claimId);
    }

    @Operation(
            summary = "Get claims for a customer",
            description = "Retrieves a list of all insurance claims for the specified customer."
    )
    @GetMapping("/customers/{customerId}/claims")
    public List<InsuranceClaim> getClaimsForCustomer(@PathVariable Long customerId) {
        return claimService.getClaimsForCustomer(customerId);
    }
}
