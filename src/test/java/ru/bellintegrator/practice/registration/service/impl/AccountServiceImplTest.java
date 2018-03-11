package ru.bellintegrator.practice.registration.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.practice.registration.dao.AccountDAO;
import ru.bellintegrator.practice.exceptionhandler.exceptions.AccountException;
import ru.bellintegrator.practice.registration.model.Account;

import static org.junit.Assert.*;

/**
 * Created on 09.03.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private AccountDAO accountDAO;

    @InjectMocks
    private AccountServiceImpl accountService;

    /**
     * Тестирует добавление успешное аккаунта (с вычислением хэша пароля и добавлением кода активации).
     */
    @Test
    public void addWhenIsOkTest() {
        Account account = new Account("name", "log", "pass");
        Mockito.when(accountDAO.getAccountByLogin(account.getLogin())).thenReturn(null); // в базе нет аккаунта с таким логином
        Mockito.when(accountDAO.save(account)).thenReturn(true);

        boolean result = accountService.add(account);

        Mockito.verify(accountDAO).save(account);
        Mockito.verify(accountDAO).getAccountByLogin(account.getLogin());
        assertTrue(result);
        assertTrue(account.getPassword().equals("10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE=")); // вычисление хэш-кода пароля было успешно
        assertTrue(account.getActivationCode() != null); // получили код активации
    }

    /**
     * Тестирует добавление аккаунта, когда логин уже занят.
     */
    @Test(expected = AccountException.class)
    public void addWhenLoginIsAlreadyInUseTest() {
        Account accountToAdd = new Account("some name", "log", "some pass");
        Account accountInDB = new Account("name", "log", "pass");
        Mockito.when(accountDAO
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
                "log",
                "10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE="); // password is hash from "pass"
        Mockito.when(accountDAO.getAccountByLogin(account.getLogin())).thenReturn(account);
        assertTrue(accountService.verifyLogin("log", "pass"));
    }

    /**
     * Тестирует поведение метода верификации логина при неправильном пароле (возвращает false)
     */
    @Test
    public void verifyLoginWhenPassIsWrongTest() {
        Account account = new Account(
                "name",
                "log",
                "10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE="); // password is hash from "pass"
        Mockito.when(accountDAO.getAccountByLogin(account.getLogin())).thenReturn(account);
        assertFalse(accountService.verifyLogin("log", "wrong pass"));
    }

    /**
     * Тестирует поведение метода верификации логина при неправильном логине (выбрасывет исключение)
     */
    @Test(expected = AccountException.class)
    public void verifyLoginWhenNoAccWithSuchLoginTest() {
        Mockito.when(accountDAO.getAccountByLogin("no acc login")).thenReturn(null);
        accountService.verifyLogin("no acc login", "pass");
    }

    /**
     * Тестирует поведение метода верификации логина при отсутствующем логине (выбрасывет исключение)
     */
    @Test(expected = AccountException.class)
    public void verifyLoginWhenLoginIsNullTest() {
        Mockito.when(accountDAO.getAccountByLogin("no acc login")).thenReturn(null);
        accountService.verifyLogin(null, "pass");
    }

    /**
     * Тестирует поведение метода верификации логина при отсутствующем пароле (выбрасывет исключение)
     */
    @Test(expected = AccountException.class)
    public void verifyLoginWhenPassIsNullTest() {
        Mockito.when(accountDAO.getAccountByLogin("no acc login")).thenReturn(null);
        accountService.verifyLogin("login", null);
    }

    /**
     * Тестирует успешную активацию аккаунта.
     */
    @Test
    public void activateAccountByCodeWhenSuccessfulTest() {
        Account account = new Account("name", "log", "pass");
        account.setActivationCode("KKpcU4HhmcMLDoBFU835whfqSY3TsFIEtxMxq2MPv94="); // = "0ZW25XEH5705317LNCN5KNCKOVRYDRX9PRATDK7BLZUFRQRBRE" to hash
        Mockito.when(accountDAO
                .getAccountByCode("KKpcU4HhmcMLDoBFU835whfqSY3TsFIEtxMxq2MPv94="))
                .thenReturn(account);

        accountService.activateAccountByCode("0ZW25XEH5705317LNCN5KNCKOVRYDRX9PRATDK7BLZUFRQRBRE"); // to hash = "KKpcU4HhmcMLDoBFU835whfqSY3TsFIEtxMxq2MPv94="

        assertTrue(account.isActivated());
        Mockito.verify(accountDAO).update(account);
    }

    /**
     * Тестирует неудачную активацию аккаунта из-за неправильного кода (выбрасывает исключение).
     */
    @Test(expected = AccountException.class)
    public void activateAccountByCodeWhenCodeIsWrongTest() {
        Mockito.when(accountDAO
                .getAccountByCode("KKpcU4HhmcMLDoBFU835whfqSY3TsFIEtxMxq2MPv94="))
                .thenReturn(null);
        accountService.activateAccountByCode("0ZW25XEH5705317LNCN5KNCKOVRYDRX9PRATDK7BLZUFRQRBRE");
    }

}