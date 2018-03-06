package ru.bellintegrator.practice.registration.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.registration.controller.AccountController;
import ru.bellintegrator.practice.registration.model.Account;
import ru.bellintegrator.practice.registration.service.AccountService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created on 05.03.2018.
 */
@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class AccountControllerImpl implements AccountController{

    private final AccountService accountService;

    @Autowired
    public AccountControllerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/register")
    public @ResponseBody String register(@RequestBody Account account) {
        String result = null;
        if (accountService.add(account)) {
            result = "success";
        }
        return result;
    }
}
