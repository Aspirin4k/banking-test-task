package com.banking.controller;

import com.banking.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.banking.repository.ClientRepository;

@RestController
@RequestMapping(path = "/client")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping(path = "")
    public @ResponseBody Iterable<ClientEntity> getClients() {
        return this.clientRepository.findAll();
    }
}
