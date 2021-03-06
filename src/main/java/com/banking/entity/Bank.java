package com.banking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE bank SET is_deleted = 1, BIC = CONCAT(BIC, '_', UNIX_TIMESTAMP(), '_deleted') WHERE id=?")
@Where(clause = "is_deleted = 0")
public class Bank implements com.banking.entity.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @NotBlank
    @Length(max = 255)
    private String name;
    @NotBlank
    @Pattern(regexp = "^\\d{9}$")
    private String BIC;
    private boolean isDeleted = false;
    @OneToMany(mappedBy = "bank", cascade = CascadeType.REMOVE)
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

    public String getBIC() {
        return BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    @JsonIgnore
    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
    }
}
