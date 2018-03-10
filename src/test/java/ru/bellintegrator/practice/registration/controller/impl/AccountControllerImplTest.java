package ru.bellintegrator.practice.registration.controller.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import ru.bellintegrator.practice.registration.model.Account;
import ru.bellintegrator.practice.registration.service.AccountService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created  on 09.03.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountControllerImplTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    AccountControllerImpl accountController;

    @Test
    public void registerTest() {
        Account account = new Account("name", "log", "pass");

        ResponseEntity<Map> result = accountController.register(account);

        verify(accountService).add(account);

        Map message = (Map) result.getBody().get("data");
        String resultMessage = (String) message.get("result");
        assertTrue(resultMessage.equals("success"));
    }

    @Test
    public void accountActivationTest() {
        String code = "code";
        accountController.accountActivation(code);
        verify(accountService).activateAccountByCode(code);
    }

    @Test
    public void loginWhenSuccessfulTest() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("login", "log");
        requestBody.put("password", "pass");
        when(accountService.verifyLogin("log", "pass")).thenReturn(true);

        ResponseEntity<Map> result = accountController.login(requestBody);

        Map message = (Map) result.getBody().get("data");
        String resultMessage = (String) message.get("result");
        assertTrue(resultMessage.equals("success"));
    }

    @Test
    public void loginWhenWrongPasswordTest() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("login", "log");
        requestBody.put("password", "pass");
        when(accountService.verifyLogin("log", "pass")).thenReturn(false);

        ResponseEntity<Map> result = accountController.login(requestBody);
        String resultMessage = (String) result.getBody().get("error");
        assertTrue(resultMessage.equals("Password is wrong!"));
    }

}