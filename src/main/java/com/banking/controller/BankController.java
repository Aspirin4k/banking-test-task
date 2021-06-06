package com.banking.controller;

import com.banking.entity.Bank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(path = "/bank")
public class BankController extends EntityController<Bank> {
}
