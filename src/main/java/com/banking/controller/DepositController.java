package com.banking.controller;

import com.banking.entity.Deposit;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(path = "/deposit")
public class DepositController extends EntityController<Deposit> {
}
