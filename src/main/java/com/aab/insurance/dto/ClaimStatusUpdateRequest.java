package com.aab.insurance.dto;

import com.aab.insurance.enums.ClaimStatus;
import jakarta.validation.constraints.NotNull;

public class ClaimStatusUpdateRequest {

    @NotNull(message = "Claim status is required")
    private ClaimStatus status;

    public ClaimStatusUpdateRequest() {}

    public ClaimStatusUpdateRequest(ClaimStatus status) {
        this.status = status;
    }

    public ClaimStatus getStatus() {
        return status;
    }
    public void setStatus(ClaimStatus status) {
        this.status = status;
    }
}
