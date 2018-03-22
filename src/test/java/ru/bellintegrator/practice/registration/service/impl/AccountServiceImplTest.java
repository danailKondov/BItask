package ru.bellintegrator.practice.registration.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bellintegrator.practice.registration.dao.AccountDAO;
import ru.bellintegrator.practice.exceptionhandler.exceptions.AccountException;
import ru.bellintegrator.practice.registration.model.Account;
import ru.bellintegrator.practice.registration.service.ComputeHashService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created on 09.03.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private ComputeHashService hashService;

    @InjectMocks
    private AccountServiceImpl accountService;

    /**
     * Тестирует добавление успешное аккаунта (с вычислением хэша пароля и добавлением кода активации).
     */
    @Test
    public void addWhenIsOkTest() {
        Account account = new Account("name", "log@mail.com", "pass");
        when(accountDAO.getAccountByLogin(account.getLogin())).thenReturn(null); // в базе нет аккаунта с таким логином
        when(accountDAO.save(account)).thenReturn(true);
        when(hashService.getSHA256HashFromString("pass")).thenReturn("10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE=");

        boolean result = accountService.add(account);

        verify(accountDAO).save(account);
        verify(accountDAO).getAccountByLogin(account.getLogin());
        assertTrue(result);
        assertTrue(account.getPassword().equals("10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE=")); // вычисление хэш-кода пароля было успешно
    }

    /**
     * Тестирует добавление аккаунта, когда логин уже занят.
     */
    @Test(expected = AccountException.class)
    public void addWhenLoginIsAlreadyInUseTest() {
        Account accountToAdd = new Account("some name", "log@mail.com", "some pass");
        Account accountInDB = new Account("name", "log@mail.com", "pass");
        when(accountDAO
                .getAccountByLogin(accountToAdd.getLogin()))
                .thenReturn(accountInDB); // в базе есть акк с таким логином
        accountService.add(accountToAdd);
    }

    /**
     * Тестирует успешную проверку логина-пароля.
     */
    @Test
    public void verifyLoginWhenIsOkTest() {
        Account account = new Account(
                "name",
                "log@mail.com",
                "10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE="); // password is hash from "pass"
        when(accountDAO.getAccountByLogin(account.getLogin())).thenReturn(account);
        when(hashService.getSHA256HashFromString("pass")).thenReturn("10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE=");
        assertTrue(accountService.verifyLogin("log@mail.com", "pass"));
    }

    /**
     * Тестирует поведение метода верификации логина при неправильном пароле (возвращает false)
     */
    @Test
    public void verifyLoginWhenPassIsWrongTest() {
        Account account = new Account(
                "name",
                "log@mail.com",
                "10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE="); // password is hash from "pass"
        when(accountDAO.getAccountByLogin(account.getLogin())).thenReturn(account);
        when(hashService.getSHA256HashFromString("wrong pass")).thenReturn("11/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE=");
        assertFalse(accountService.verifyLogin("log@mail.com", "wrong pass"));
    }

    /**
     * Тестирует поведение метода верификации логина при неправильном логине (выбрасывет исключение)
     */
    @Test(expected = AccountException.class)
    public void verifyLoginWhenNoAccWithSuchLoginTest() {
        when(accountDAO.getAccountByLogin("no_acc_login@mail.com")).thenReturn(null);
        when(hashService.getSHA256HashFromString("pass")).thenReturn("10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE=");
        accountService.verifyLogin("no_acc_login@mail.com", "pass");
    }

    /**
     * Тестирует поведение метода верификации логина при отсутствующем логине (выбрасывет исключение)
     */
    @Test(expected = AccountException.class)
    public void verifyLoginWhenLoginIsNullTest() {
        when(accountDAO.getAccountByLogin("no_acc_login@mail.com")).thenReturn(null);
        accountService.verifyLogin(null, "pass");
    }

    /**
     * Тестирует поведение метода верификации логина при отсутствующем пароле (выбрасывет исключение)
     */
    @Test(expected = AccountException.class)
    public void verifyLoginWhenPassIsNullTest() {
        when(accountDAO.getAccountByLogin("no_acc_login@mail.com")).thenReturn(null);
        accountService.verifyLogin("login", null);
    }

    /**
     * Тестирует успешную активацию аккаунта.
     */
    @Test
    public void activateAccountByCodeWhenSuccessfulTest() {
        Account account = new Account("name", "log@mail.com", "pass");
        account.setActivationCode("KKpcU4HhmcMLDoBFU835whfqSY3TsFIEtxMxq2MPv94="); // = "0ZW25XEH5705317LNCN5KNCKOVRYDRX9PRATDK7BLZUFRQRBRE" to hash
        when(accountDAO
                .getAccountByCode("KKpcU4HhmcMLDoBFU835whfqSY3TsFIEtxMxq2MPv94="))
                .thenReturn(account);
        when(hashService.getSHA256HashFromString("0ZW25XEH5705317LNCN5KNCKOVRYDRX9PRATDK7BLZUFRQRBRE")).
                thenReturn("KKpcU4HhmcMLDoBFU835whfqSY3TsFIEtxMxq2MPv94=");

        accountService.activateAccountByCode("0ZW25XEH5705317LNCN5KNCKOVRYDRX9PRATDK7BLZUFRQRBRE"); // to hash = "KKpcU4HhmcMLDoBFU835whfqSY3TsFIEtxMxq2MPv94="

        assertTrue(account.isActivated());
        verify(accountDAO).update(account);
    }

    /**
     * Тестирует неудачную активацию аккаунта из-за неправильного кода (выбрасывает исключение).
     */
    @Test(expected = AccountException.class)
    public void activateAccountByCodeWhenCodeIsWrongTest() {
        when(accountDAO
                .getAccountByCode("KKpcU4HhmcMLDoBFU835whfqSY3TsFIEtxMxq2MPv94="))
                .thenReturn(null);
        when(hashService.getSHA256HashFromString("0ZW25XEH5705317LNCN5KNCKOVRYDRX9PRATDK7BLZUFRQRBRE")).
                thenReturn("KKpcU4HhmcMLDoBFU835whfqSY3TsFIEtxMxq2MPv94=");

        accountService.activateAccountByCode("0ZW25XEH5705317LNCN5KNCKOVRYDRX9PRATDK7BLZUFRQRBRE");
    }

}