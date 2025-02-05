package com.aab.insurance.dto;

import com.aab.insurance.enums.InsuranceType;
import java.time.LocalDate;
import java.util.Set;

public class CustomerResponse {
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private Set<InsuranceType> insuranceTypes;

    public CustomerResponse(Long id, String fullName, LocalDate dateOfBirth, Set<InsuranceType> insuranceTypes) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.insuranceTypes = insuranceTypes;
    }

    public Long getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public Set<InsuranceType> getInsuranceTypes() {
        return insuranceTypes;
    }
}
