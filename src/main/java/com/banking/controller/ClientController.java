package com.banking.controller;

import com.banking.entity.Client;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(path = "/client")
public class ClientController extends EntityController<Client> {
}
