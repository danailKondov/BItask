package ru.bellintegrator.practice.registration.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.registration.controller.AccountController;
import ru.bellintegrator.practice.registration.model.Account;
import ru.bellintegrator.practice.registration.service.AccountService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер для работы с аккаунтами: регистрации новых аккаунтов, их активации и проверки логинов-паролей.
 *
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

    @Override
    @PostMapping(value = "/register")
    public ResponseEntity<Map> register(@RequestBody @Valid Account account) {
        accountService.add(account);
        return new ResponseEntity<>(getSuccessMessageWrapper(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/activation")
    public void accountActivation(@RequestParam("code") String code) {
        accountService.activateAccountByCode(code);
    }

    @Override
    @PostMapping(value = "/login")
    public ResponseEntity<Map> login(@RequestBody Map<String, String> map) {
        if(accountService.verifyLogin(map.get("login"), map.get("password"))) {
            return new ResponseEntity<>(getSuccessMessageWrapper(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(getErrorMessageWrapper(), HttpStatus.BAD_REQUEST);
        }
    }

    private Map getErrorMessageWrapper() {
        Map<String, String> result = new HashMap<>();
        result.put("error", "Password is wrong!");
        return result;
    }

    private Map<String, Map> getSuccessMessageWrapper() {
        Map<String, Map> result = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        data.put("result", "success");
        result.put("data", data);
        return result;
    }
}
