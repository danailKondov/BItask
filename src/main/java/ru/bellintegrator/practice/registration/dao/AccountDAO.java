package ru.bellintegrator.practice.registration.dao;

import ru.bellintegrator.practice.registration.model.Account;

import java.util.List;

/**
 * DAO для работы с логинами.
 */
public interface AccountDAO {

    /**
     * Сохранить новый аккаунт.
     * @param account логин
     * @return true есди сохранение прошло успешно
     */
    boolean save(Account account);

    /**
     * Получить аккаунт с данным логином.
     * @param login логин
     * @return аккаунт с данным логином
     */
    Account getAccountByLogin(String login);

    /**
     * Получить все аккаунты.
     * @return список аккаунтов
     */
    List<Account> getAllAccounts();

    /**
     * Получить аккаунт с данным кодом активации.
     * @param hashFromActivationCode код активации
     * @return аккаунт
     */
    Account getAccountByCode(String hashFromActivationCode);

    /**
     * Обновить аккаунт.
     * @param account акк
     */
    void update(Account account);

    /**
     * Удалить все аккаунты.
     */
    void deleteAllAccounts();
}
