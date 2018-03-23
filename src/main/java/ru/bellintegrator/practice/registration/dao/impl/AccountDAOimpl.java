package ru.bellintegrator.practice.registration.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.registration.dao.AccountDAO;
import ru.bellintegrator.practice.registration.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

/**
 * DAO для работы с пользователями.
 */
@Repository
@Transactional
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
    @Transactional(readOnly = true)
    public Account getAccountByLogin(String login) {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
            Root<Account> root = criteriaQuery.from(Account.class);
            TypedQuery<Account> typedQuery = em.createQuery(criteriaQuery.where(criteriaBuilder.equal(root.get("login"), login)));
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
            Root<Account> root = criteriaQuery.from(Account.class);
            TypedQuery<Account> typedQuery = em.createQuery(criteriaQuery.select(root));
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountByCode(String code) {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
            Root<Account> root = criteriaQuery.from(Account.class);
            TypedQuery<Account> typedQuery = em.createQuery(criteriaQuery.where(criteriaBuilder.equal(root.get("activationCode"), code)));
            return typedQuery.getSingleResult();
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
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaDelete<Account> criteriaDelete = criteriaBuilder.createCriteriaDelete(Account.class);
        criteriaDelete.from(Account.class);
        em.createQuery(criteriaDelete).executeUpdate();
    }
}
