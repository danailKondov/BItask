package ru.bellintegrator.practice.registration.controller;

import org.springframework.http.ResponseEntity;
import ru.bellintegrator.practice.registration.model.Account;

import java.util.Map;

/**
 * Created on 05.03.2018.
 */
public interface AccountController {

    ResponseEntity<Map> register(Account account);
    void accountActivation(String code);
    ResponseEntity<Map> login(Map<String, String> map);
}
