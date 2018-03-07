package ru.bellintegrator.practice.registration.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.registration.dao.AccountDAO;
import ru.bellintegrator.practice.registration.model.Account;

import javax.validation.constraints.AssertTrue;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created on 07.03.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class AccountDAOimplTest {

    @Autowired
    private AccountDAO accountDAO;

    @Test
    public void saveTest() {
        Account account = new Account("name", "log", "pass");
        account.setActivationCode("activation code");
        accountDAO.save(account);
        List<Account> accounts = accountDAO.getAllAccounts();
        assertTrue(accounts.contains(account));
    }

    @Test
    public void getAccByLoginTest() {
        Account account = new Account("name", "log", "pass");
        account.setActivationCode("activation code");
        accountDAO.save(account);
        Account account1 = accountDAO.getAccountByLogin("log");
        assertTrue(account.equals(account1));
    }

    @Test
    public void getAccByLoginWhenNoAccTest() {
        Account account = accountDAO.getAccountByLogin("no account with such login in DB");
        assertNull(account);
    }

    @Test
    public void getAllAccsWhenThereIsAccsTest() {
        accountDAO.deleteAllAccounts();
        Account account = new Account("name", "log", "pass");
        account.setActivationCode("activation code");
        accountDAO.save(account);
        Account account2 = new Account("name2", "log2", "pass2");
        account2.setActivationCode("activation code2");
        accountDAO.save(account2);
        Account account3 = new Account("name3", "log3", "pass3");
        account3.setActivationCode("activation code3");
        accountDAO.save(account3);

        List<Account> result = accountDAO.getAllAccounts();
        assertTrue(result.size() == 3);
        assertTrue(result.contains(account));
        assertTrue(result.contains(account2));
        assertTrue(result.contains(account3));
    }

    @Test
    public void getAllAccsWhenThereIsNoAccsTest() {
        accountDAO.deleteAllAccounts();
        List<Account> result = accountDAO.getAllAccounts();
        assertTrue(result.size() == 0);
    }

    @Test
    public void getAccByCodeWhenThereIsAccTest() {
        accountDAO.deleteAllAccounts();
        Account account = new Account("name", "log", "pass");
        account.setActivationCode("activation code");
        accountDAO.save(account);

        Account account1 = accountDAO.getAccountByCode("activation code");

        assertTrue(account.equals(account1));
    }

    @Test
    public void getAccsByCodeWhenThereIsNoAccsTest() {
        Account account = accountDAO.getAccountByCode("no such code");
        assertNull(account);
    }

    @Test
    public void updateTest() {
        accountDAO.deleteAllAccounts();
        Account account = new Account("name", "log", "pass");
        account.setActivationCode("activation code");
        accountDAO.save(account);

        account.setName("new name");
        accountDAO.update(account);

        Account account1 = accountDAO.getAccountByLogin("log");
        assertTrue(account1.getName().equals("new name"));
    }
}