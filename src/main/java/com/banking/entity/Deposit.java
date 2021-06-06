package com.banking.entity;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
public class Deposit implements com.banking.entity.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @PastOrPresent
    private LocalDate dateOpened;
    @Min(0)
    @Max(100)
    @NotNull
    private Double percent;
    @Min(1)
    @NotNull
    private Integer months;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Client getClient() {
        return client;
    }

    public String getClientId() {
        return client.getId();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Bank getBank() {
        return bank;
    }

    public String getBankId() {
        return bank.getId();
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
