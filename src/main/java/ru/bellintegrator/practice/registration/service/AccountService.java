package ru.bellintegrator.practice.registration.service;

import ru.bellintegrator.practice.registration.model.Account;

/**
 * Created on 05.03.2018.
 */
public interface AccountService {

    boolean add(Account account);

    boolean verifyLogin (String login, String password);

    boolean activateAccountByCode(String code);
}
