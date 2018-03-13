package ru.bellintegrator.practice.registration.controller;

import org.springframework.http.ResponseEntity;
import ru.bellintegrator.practice.registration.model.Account;

import java.util.Map;

/**
 * Created on 05.03.2018.
 */
public interface AccountController {

    ResponseEntity register(Account account);
    void accountActivation(String code);
    ResponseEntity login(Map<String, String> map);
}
