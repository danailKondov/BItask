package ru.bellintegrator.practice.registration.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.registration.dao.AccountDAO;
import ru.bellintegrator.practice.registration.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO для работы с пользователями.
 */
@Repository
public class AccountDAOimpl implements AccountDAO {

    private final EntityManager em;

    @Autowired
    public AccountDAOimpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean save(Account account) {
        em.persist(account);
        em.flush();
        return em.contains(account);
    }

    @Override
    public Account getAccountByLogin(String login) {
        TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.login = :login", Account.class); // почему не распознает Account?
        return query.getSingleResult();
    }

    @Override
    public List<Account> getAllAccounts() {
        TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a", Account.class); // почему не распознает Account?
        return query.getResultList();
    }

    @Override
    public Account getAccountByCode(String hashFromActivationCode) {
        TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.activationCode = :hashFromActivationCode", Account.class);
        return query.getSingleResult();
    }

//    @Override
//    public void update(Account account) {
//        em.merge(account); // update?
//    }
}
