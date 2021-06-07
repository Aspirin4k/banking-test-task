package com.banking.controller;

import com.banking.dto.DepositSearch;
import com.banking.entity.Deposit;
import com.banking.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/deposit")
public class DepositController extends EntityController<Deposit> {
    @Autowired
    protected DepositRepository repo;

    @PostMapping(path = "/search")
    public @ResponseBody Iterable<Deposit> search(
            @Valid @RequestBody DepositSearch criteria
    ) {
        return this.repo.findAll(criteria.toSpecification(), criteria.toPageable());
    }
}
