package com.banking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
public class Client implements com.banking.entity.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String shortName;
    @NotBlank
    private String address;
    @NotNull
    private LegalEntityType legalType;
    @OneToMany(mappedBy = "client")
    private Set<Deposit> deposits;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LegalEntityType getLegalType() {
        return legalType;
    }

    public void setLegalType(LegalEntityType legalType) {
        this.legalType = legalType;
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    @JsonIgnore
    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
    }
}
