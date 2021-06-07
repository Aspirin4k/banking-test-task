package com.banking.repository;

import com.banking.entity.Deposit;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class DepositSpecification {
    public static Specification<Deposit> older(LocalDate date) {
        return (deposit, cq, cb) -> cb.lessThan(deposit.get("dateOpened"), date);
    }

    public static Specification<Deposit> newer(LocalDate date) {
        return (deposit, cq, cb) -> cb.greaterThan(deposit.get("dateOpened"), date);
    }

    public static Specification<Deposit> forBankBIC(String bic) {
        return (deposit, cq, cb) -> cb.equal(deposit.get("bank").get("BIC"), bic);
    }
}
