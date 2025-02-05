package com.aab.insurance.dto;

import com.aab.insurance.enums.InsuranceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class ClaimRequest {

    @NotNull(message = "Claim type is required")
    private InsuranceType claimType;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    @NotNull(message = "Cost is required")
    @Positive(message = "Cost must be a positive value")
    private Double cost;

    public ClaimRequest() {}

    public ClaimRequest(InsuranceType claimType, LocalDate date, Double cost) {
        this.claimType = claimType;
        this.date = date;
        this.cost = cost;
    }

    public InsuranceType getClaimType() {
        return claimType;
    }
    public void setClaimType(InsuranceType claimType) {
        this.claimType = claimType;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getCost() {
        return cost;
    }
    public void setCost(Double cost) {
        this.cost = cost;
    }
}
