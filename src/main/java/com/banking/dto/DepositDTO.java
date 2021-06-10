package com.banking.dto;

import javax.persistence.Transient;
import javax.validation.constraints.*;
import java.time.LocalDate;

public class DepositDTO {
    @PastOrPresent
    private LocalDate dateOpened;
    @DecimalMin("0.1")
    @DecimalMax("100")
    @NotNull
    private Double percent;
    @Min(1)
    @NotNull
    private Integer months;
    @NotBlank
    @Transient
    private String clientId;
    @NotBlank
    @Transient
    private String bankId;

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDate dateOpened) {
        this.dateOpened = dateOpened;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}
