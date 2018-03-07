package ru.bellintegrator.practice.registration.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.registration.dao.AccountDAO;
import ru.bellintegrator.practice.registration.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
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
        try {
            TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.login = :login", Account.class)
                    .setParameter("login", login);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        try {
            TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a", Account.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Account getAccountByCode(String code) {
        try {
            TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.activationCode = :code", Account.class)
                    .setParameter("code", code);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void update(Account account) {
        em.merge(account);
    }

    @Override
    public void deleteAllAccounts() {
        em.createQuery("DELETE FROM Account").executeUpdate();
    }
}
