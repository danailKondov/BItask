package ru.bellintegrator.practice.registration.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.registration.exceptions.AccountException;
import ru.bellintegrator.practice.registration.dao.AccountDAO;
import ru.bellintegrator.practice.registration.model.Account;
import ru.bellintegrator.practice.registration.service.AccountService;

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

    /**
     * Поле является имитацией части функционала отправки кода подтверждения регистрации,
     * отсутствующего в учебном проекте.
     */
    private String codeForActivation;

    @Autowired
    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    @Transactional
    public boolean add(Account account) {
        try {
            if (loginIsFree(account.getLogin())) {
                changePassToHash(account);
                setActivationCode(account);
                return accountDAO.save(account);
            } else {
                log.debug("Подобный логин уже существует: " + account.getLogin());
                throw new AccountException("Подобный логин уже существует: " + account.getLogin());
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new AccountException("Ошибка создания аккаунта: ошибка кодирования пароля", e);
        }
    }

    /**
     * Генерирует случайный код активации и вставляем его хэш в аккаунт, предварительно закодировав в base64.
     * @param account акк
     */
    private void setActivationCode(Account account) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String activationCode = getRandomString();
        codeForActivation = activationCode;
        String hashFromActivationCode = getSHA256HashFromString(activationCode);
        account.setActivationCode(hashFromActivationCode);
    }

    /**
     * Заменяет пароль на хэш SHA256 в аккаунте для его безопасного хранения.
     * @param account акк
     */
    private void changePassToHash(Account account) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String hashFromPass = getSHA256HashFromString(account.getPassword());
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
        try {
            if (login == null || password == null) { // нужна ли подобная проверка?
                return false;
            } else {
                String hashFromPass = getSHA256HashFromString(password);
                Account account = accountDAO.getAccountByLogin(login);
                if (account == null) {
                    throw new AccountException("Нет аккаунта с подобным логином в базе данных: " + login);
                } else {
                    return hashFromPass.equals(account.getPassword());
                }
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new AccountException("Ошибка верификации логина: ошибка кодирования пароля", e);
        }
    }

    private boolean loginIsFree(String login) {
        return accountDAO.getAccountByLogin(login) == null;
    }

    private String getSHA256HashFromString(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest encoder = MessageDigest.getInstance("SHA-256");
        byte[] digest = encoder.digest(source.getBytes("UTF-8"));
        return new String(Base64.getEncoder().encode(digest));
    }

    @Override
    @Transactional
    public boolean activateAccountByCode(String code) {
        try {
            String hashFromActivationCode = getSHA256HashFromString(code);
            Account account = accountDAO.getAccountByCode(hashFromActivationCode);
            if (account != null) {
                account.setActivated(true);
                accountDAO.update(account);
                return true;
            } else {
                throw new AccountException("Нет аккаунта с данным кодом активации: " + code);
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new AccountException("Ошибка активации аккаунта: ошибка кодирования кода активации", e);
        }
    }

    /**
     * Метод является имитацией части функционала отправки кода подтверждения регистрации,
     * отсутствующего в учебном проекте, но необходимого для тестирования.
     * @return код активации
     */
    public String getCodeForActivation() {
        return codeForActivation;
    }
}
