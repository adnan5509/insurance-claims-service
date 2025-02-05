package com.aab.insurance.model;

import com.aab.insurance.enums.InsuranceType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private LocalDate dateOfBirth;

    @ElementCollection(targetClass = InsuranceType.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "customer_insurance_types", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "insurance_type")
    private Set<InsuranceType> insuranceTypes = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InsuranceClaim> claims = new ArrayList<>();

    public Customer() {
    }

    public Customer(String fullName, LocalDate dateOfBirth, Set<InsuranceType> insuranceTypes) {
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

    public List<InsuranceClaim> getClaims() {
        return claims;
    }

    public void setClaims(List<InsuranceClaim> claims) {
        this.claims = claims;
    }
}
