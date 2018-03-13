package ru.bellintegrator.practice.registration.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.Utils.CustomErrorResponse;
import ru.bellintegrator.practice.Utils.CustomSuccessResponse;
import ru.bellintegrator.practice.registration.controller.AccountController;
import ru.bellintegrator.practice.registration.model.Account;
import ru.bellintegrator.practice.registration.service.AccountService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер для работы с аккаунтами: регистрации новых аккаунтов, их активации и проверки логинов-паролей.
 *
 * Created on 05.03.2018.
 */
@RestController
@RequestMapping(value = "/api")
public class AccountControllerImpl implements AccountController{

    private final AccountService accountService;

    @Autowired
    public AccountControllerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid Account account) {
        accountService.add(account);
        return new ResponseEntity<>(new CustomSuccessResponse(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/activation")
    public void accountActivation(@RequestParam("code") String code) {
        accountService.activateAccountByCode(code);
    }

    @Override
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> map) {
        if(accountService.verifyLogin(map.get("login"), map.get("password"))) {
            return new ResponseEntity<>(new CustomSuccessResponse(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomErrorResponse("Password is wrong!"), HttpStatus.BAD_REQUEST);
        }
    }
}
