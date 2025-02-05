package com.aab.insurance.model;

import com.aab.insurance.enums.ClaimStatus;
import com.aab.insurance.enums.InsuranceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class InsuranceClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InsuranceType claimType;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Customer customer;

    public InsuranceClaim() {
    }

    public InsuranceClaim(InsuranceType claimType, LocalDate date, ClaimStatus status, double cost, Customer customer) {
        this.claimType = claimType;
        this.date = date;
        this.status = status;
        this.cost = cost;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
