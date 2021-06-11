package com.banking.controller;

import com.banking.dto.DepositDTO;
import com.banking.dto.DepositSearch;
import com.banking.entity.Bank;
import com.banking.entity.Client;
import com.banking.entity.Deposit;
import com.banking.exception.EntityNotFoundException;
import com.banking.repository.BankRepository;
import com.banking.repository.ClientRepository;
import com.banking.repository.DepositRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
public class DepositController extends EntityController<Deposit, DepositDTO> {
    @Autowired
    protected DepositRepository repo;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ClientRepository clientRepository;
    private ModelMapper modelMapper;

    public DepositController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(
                new PropertyMap<DepositDTO, Deposit>() {
                    @Override
                    protected void configure() {
                        skip().setId(null);
                    }
                }
        );
    }

    @PostMapping(path = "/search")
    public Iterable<Deposit> search(
            @Valid @RequestBody DepositSearch criteria
    ) {
        return this.repo.findAll(criteria.toSpecification(), criteria.toPageable());
    }

    @Override
    protected Deposit convertDTOtoEntity(DepositDTO dto) throws EntityNotFoundException {
        Optional<Bank> bank = this.bankRepository.findById(dto.getBankId());
        if (bank.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Optional<Client> client = this.clientRepository.findById(dto.getClientId());
        if (client.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Deposit deposit = this.modelMapper.map(dto, Deposit.class);
        deposit.setBank(bank.get());
        deposit.setClient(client.get());
        return deposit;
    }
}
