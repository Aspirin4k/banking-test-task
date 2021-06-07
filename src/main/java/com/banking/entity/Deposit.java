package com.banking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@SQLDelete(sql = "UPDATE deposit SET is_deleted = 1 WHERE id=?")
@Where(clause = "is_deleted = 0")
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
    private boolean isDeleted = false;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @NotBlank
    @Transient
    private String clientId;
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;
    @NotBlank
    @Transient
    private String bankId;

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
        return null == client ? clientId : client.getId();
    }

    @JsonIgnore
    public void setClient(Client client) {
        this.client = client;
    }

    public Bank getBank() {
        return bank;
    }

    public String getBankId() {
        return null == bank ? bankId : bank.getId();
    }

    @JsonIgnore
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}
