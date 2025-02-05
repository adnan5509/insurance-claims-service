package com.aab.insurance.dto;

import com.aab.insurance.enums.InsuranceType;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public class CustomerRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    private String fullName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Insurance types cannot be null")
    @Size(min = 1, message = "At least one insurance type must be selected")
    private Set<InsuranceType> insuranceTypes;

    // Constructors
    public CustomerRequest() {}

    public CustomerRequest(String fullName, LocalDate dateOfBirth, Set<InsuranceType> insuranceTypes) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.insuranceTypes = insuranceTypes;
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<InsuranceType> getInsuranceTypes() {
        return insuranceTypes;
    }
    public void setInsuranceTypes(Set<InsuranceType> insuranceTypes) {
        this.insuranceTypes = insuranceTypes;
    }
}
