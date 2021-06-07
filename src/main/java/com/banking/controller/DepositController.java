package com.banking.controller;

import com.banking.dto.DepositSearch;
import com.banking.entity.Bank;
import com.banking.entity.Client;
import com.banking.entity.Deposit;
import com.banking.exception.EntityNotFoundException;
import com.banking.repository.BankRepository;
import com.banking.repository.ClientRepository;
import com.banking.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(path = "/deposit")
public class DepositController extends EntityController<Deposit> {
    @Autowired
    protected DepositRepository repo;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ClientRepository clientRepository;

    @PostMapping(path = "/search")
    public Iterable<Deposit> search(
            @Valid @RequestBody DepositSearch criteria
    ) {
        return this.repo.findAll(criteria.toSpecification(), criteria.toPageable());
    }

    @PostMapping(path = "")
    public ResponseEntity<Map<String, String>> createEntity(
            @Valid @RequestBody Deposit entity
    ) throws EntityNotFoundException {
        Optional<Bank> bank = this.bankRepository.findById(entity.getBankId());
        if (bank.isEmpty()) {
            throw new EntityNotFoundException();
        }
        entity.setBank(bank.get());

        Optional<Client> client = this.clientRepository.findById(entity.getClientId());
        if (client.isEmpty()) {
            throw new EntityNotFoundException();
        }
        entity.setClient(client.get());

        return super.createEntity(entity);
    }
}
