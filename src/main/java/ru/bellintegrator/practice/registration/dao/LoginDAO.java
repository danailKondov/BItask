package ru.bellintegrator.practice.registration.dao;

import ru.bellintegrator.practice.registration.model.Login;

/**
 * DAO для работы с логинами.
 */
public interface LoginDAO {

    boolean register(Login login);
    boolean verifyLogin(String login, String password);
}
