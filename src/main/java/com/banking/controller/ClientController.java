package com.banking.controller;

import com.banking.dto.ClientDTO;
import com.banking.dto.DepositDTO;
import com.banking.entity.Client;
import com.banking.entity.Deposit;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(path = "/client")
public class ClientController extends EntityController<Client, ClientDTO> {
    private ModelMapper modelMapper;

    public ClientController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(
                new PropertyMap<ClientDTO, Client>() {
                    @Override
                    protected void configure() {
                        skip().setId(null);
                    }
                }
        );
    }

    @Override
    protected Client convertDTOtoEntity(ClientDTO clientDTO) {
        return this.modelMapper.map(clientDTO, Client.class);
    }
}
