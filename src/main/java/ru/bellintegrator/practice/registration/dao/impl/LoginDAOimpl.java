package ru.bellintegrator.practice.registration.dao.impl;

import ru.bellintegrator.practice.registration.dao.LoginDAO;
import ru.bellintegrator.practice.registration.model.Login;

/**
 * DAO для работы с пользователями.
 */
public class LoginDAOimpl implements LoginDAO {

    @Override
    public boolean register(Login login) {
        return false;
    }

    @Override
    public boolean verifyLogin(String login, String password) {
        return false;
    }
}
