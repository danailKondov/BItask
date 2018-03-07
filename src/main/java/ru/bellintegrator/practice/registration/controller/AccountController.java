package ru.bellintegrator.practice.registration.controller;

import ru.bellintegrator.practice.registration.model.Account;

import java.util.Map;

/**
 * Created on 05.03.2018.
 */
public interface AccountController {

    Map register(Account account);
    void accountActivation(String code);
}
