package com.banking.dto;

import com.banking.entity.Deposit;
import com.banking.repository.DepositSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DepositSearch {
    private LocalDate older;
    private LocalDate newer;
    @Size(min = 9, max = 9)
    private String bic;
    @Pattern(regexp = "ASC|DESC")
    private String dateOpenedOrder;
    @Min(0)
    private Integer page = 0;
    @Max(50)
    @Min(1)
    private Integer size = 50;

    public void setOlder(LocalDate older) {
        this.older = older;
    }

    public void setNewer(LocalDate newer) {
        this.newer = newer;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setDateOpenedOrder(String dateOpenedOrder) {
        this.dateOpenedOrder = dateOpenedOrder;
    }

    public Specification<Deposit> toSpecification() {
        Specification<Deposit> specification = Specification.where(null);

        if (null != this.older) {
            specification = specification.and(DepositSpecification.older(this.older));
        }

        if (null != this.newer) {
            specification = specification.and(DepositSpecification.newer(this.newer));
        }

        if (null != this.bic) {
            specification = specification.and(DepositSpecification.forBankBIC(this.bic));
        }

        return specification;
    }

    public Pageable toPageable() {
        ArrayList<Sort.Order> orders = new ArrayList<>(2);

        if (null != this.dateOpenedOrder) {
            orders.add(this.dateOpenedOrder.equals("ASC")
                ? Sort.Order.asc("dateOpened")
                : Sort.Order.desc("dateOpened")
            );
        }

        orders.add(Sort.Order.asc("id"));
        Sort sort = Sort.by(orders);
        return PageRequest.of(this.page, this.size, sort);
    }
}
