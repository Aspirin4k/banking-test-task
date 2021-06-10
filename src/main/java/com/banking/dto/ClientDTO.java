package com.banking.dto;

import com.banking.entity.LegalEntityType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClientDTO {
    @NotBlank
    @Length(max = 255)
    private String name;
    @NotBlank
    @Length(max = 255)
    private String shortName;
    @NotBlank
    @Length(max = 255)
    private String address;
    @NotNull
    private LegalEntityType legalType;

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
}
