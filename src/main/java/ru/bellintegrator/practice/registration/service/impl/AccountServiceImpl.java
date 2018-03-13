package ru.bellintegrator.practice.registration.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.exceptionhandler.exceptions.AccountException;
import ru.bellintegrator.practice.registration.dao.AccountDAO;
import ru.bellintegrator.practice.registration.model.Account;
import ru.bellintegrator.practice.registration.service.AccountService;
import ru.bellintegrator.practice.registration.service.ComputeHashService;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

/**
 * Класс отвечает за управление аккаунтами.
 *
 * Общая логика активации аккаунта:
 * 1. При регистрации создать неактивного пользователя
 * 2. Сгенерировать случайную строку. Хэш от неё записать в бд
 * 3. Сам email отправлять не надо. Добавить запись в БД. Предполагаем, что их обрабатывает отдельный сервис отправки email. Его делать не надо
 * 4. Контроллер activation берёт хэш от значения code и ищет по ней запись. Если находит, делает активным соответствующего пользователя
 *
 * Для хэша используется SHA-256. Получение его вынесено в отдельный сервис.
 *
 * Created on 05.03.2018.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES) //?
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountDAO accountDAO;

    private final ComputeHashService hashService;

    /**
     * Поле является имитацией части функционала отправки кода подтверждения регистрации,
     * отсутствующего в учебном проекте.
     */
    private String codeForActivation;

    @Autowired
    public AccountServiceImpl(AccountDAO accountDAO, ComputeHashService hashService) {
        this.accountDAO = accountDAO;
        this.hashService = hashService;
    }

    @Override
    @Transactional
    public boolean add(Account account) {
            if (loginIsFree(account.getLogin())) {
                changePassToHash(account);
                setActivationCode(account);
                return accountDAO.save(account);
            } else {
                throw new AccountException("Подобный логин уже существует: " + account.getLogin());
            }
    }

    /**
     * Генерирует случайный код активации и вставляем его хэш в аккаунт, предварительно закодировав в base64.
     * @param account акк
     */
    private void setActivationCode(Account account) {
        String activationCode = getRandomString();
        codeForActivation = activationCode;
        String hashFromActivationCode = hashService.getSHA256HashFromString(activationCode);
        account.setActivationCode(hashFromActivationCode);
    }

    /**
     * Заменяет пароль на хэш SHA256 в аккаунте для его безопасного хранения.
     * @param account акк
     */
    private void changePassToHash(Account account) {
        String hashFromPass = hashService.getSHA256HashFromString(account.getPassword());
        account.setPassword(hashFromPass);
    }

    private String getRandomString() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            int number = random.nextInt(chars.length());
            char ch = chars.charAt(number);
            builder.append(ch);
        }
        return builder.toString();
    }

    @Override
    @Transactional (readOnly = true)
    public boolean verifyLogin(String login, String password) {
            if (login == null || password == null) { // нужна ли подобная проверка?
                throw new AccountException("Отсутствует логин или пароль");
            } else {
                String hashFromPass = hashService.getSHA256HashFromString(password);
                Account account = accountDAO.getAccountByLogin(login);
                if (account == null) {
                    throw new AccountException("Нет аккаунта с подобным логином в базе данных: " + login);
                } else {
                    return hashFromPass.equals(account.getPassword());
                }
            }
    }

    private boolean loginIsFree(String login) {
        return accountDAO.getAccountByLogin(login) == null;
    }

    @Override
    @Transactional
    public boolean activateAccountByCode(String code) {
            String hashFromActivationCode = hashService.getSHA256HashFromString(code);
            Account account = accountDAO.getAccountByCode(hashFromActivationCode);
            if (account != null) {
                account.setActivated(true);
                accountDAO.update(account);
                return true;
            } else {
                throw new AccountException("Нет аккаунта с данным кодом активации: " + code);
            }
    }

    /**
     * Метод является имитацией части функционала отправки кода подтверждения регистрации,
     * отсутствующего в учебном проекте, но необходимого для тестирования.
     * @return код активации
     */
    @Override
    public String getCodeForActivation() {
        return codeForActivation;
    }
}
