package com.banking.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BankDTO {
    @NotBlank
    @Length(max = 255)
    private String name;
    @NotBlank
    @Lob
    @Pattern(regexp = "^\\d{9}$")
    private String BIC;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBIC() {
        return BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }
}
