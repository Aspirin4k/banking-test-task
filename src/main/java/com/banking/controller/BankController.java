package com.banking.controller;

import com.banking.dto.BankDTO;
import com.banking.dto.ClientDTO;
import com.banking.entity.Bank;
import com.banking.entity.Client;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(path = "/bank")
public class BankController extends EntityController<Bank, BankDTO> {
    private ModelMapper modelMapper;

    public BankController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(
                new PropertyMap<BankDTO, Bank>() {
                    @Override
                    protected void configure() {
                        skip().setId(null);
                    }
                }
        );
    }

    @Override
    protected Bank convertDTOtoEntity(BankDTO dto) {
        return this.modelMapper.map(dto, Bank.class);
    }
}
