package ru.bellintegrator.practice.registration.controller.impl;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.registration.dao.AccountDAO;
import ru.bellintegrator.practice.registration.model.Account;
import ru.bellintegrator.practice.registration.service.AccountService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created 09.03.2018.
 *
 * see: https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4
 */
@RunWith(SpringRunner.class) // tells JUnit to run using Spring’s testing support.
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, //container will start at any random port
        classes = Application.class)
public class AccountControllerImplIntegrationTest {

    /**
     * Заметки для разработки. Потом уберу.
     *
     * Note that TestRestTemplate is now available as bean whenever @SpringBootTest is used.
     * It’s pre-configured to resolve relative paths to http://localhost:${local.server.port}.
     *
     * => @LocalServerPort не нужен
     *    private int port;
     */
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountDAO accountDAO;

    // заголовок запроса, в т.ч. определяет тип передаваемых/принимаемых значений (Accept),
    // например: headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    private HttpHeaders headers;

    @Before
    public void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Тест успешной регистрации аккаунта.
     */
    @Test
    public void registerWhenSuccessfulTest() throws JSONException {
        Account account = new Account("some name", "some log", "pass");
        HttpEntity<Account> entity = new HttpEntity<>(account, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/register",
                HttpMethod.POST, entity, String.class);

        String expected = "{\"data\":{\"result\":\"success\"}}";
        JSONAssert.assertEquals(expected, response.getBody(), false); // выдано подтверждающее сообщение в формате JSON
        assertNotNull(accountDAO.getAccountByLogin("some log")); // аккаунт в базе данных
    }

    /**
     * Тест регистрации аккаунта, когда логин уже занят.
     */
    @Test
    public void registerWhenLoginIsAlreadyInUseTest() throws JSONException {
        Account accountInDB = new Account("some name", "same log", "pass");
        accountService.add(accountInDB);

        Account accountToAdd = new Account("new name", "same log", "new pass");
        HttpEntity<Account> entity = new HttpEntity<>(accountToAdd, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/register",
                HttpMethod.POST, entity, String.class);

        String expected = "{\"error\":\"Подобный логин уже существует: same log\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    /**
     * Тест регистрации аккаунта, когда логин не проходит валидацию (слишком короткий - должен быть минимум 3 символа).
     */
    @Test
    public void registerWhenLoginIsTooShortTest() throws JSONException {
        Account accountToAdd = new Account("new name", "sa", "new pass");
        HttpEntity<Account> entity = new HttpEntity<>(accountToAdd, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/register",
                HttpMethod.POST, entity, String.class);

        String expected = "{\"error\":\"Ошибка валидации при регистрации аккаунта: size must be between 3 and 50  \"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    /**
     * Тест регистрации аккаунта, когда пароль не проходит валидацию (слишком короткий - должен быть минимум 3 символа).
     */
    @Test
    public void registerWhenPasswordIsTooShortTest() throws JSONException {
        Account accountToAdd = new Account("new name", "sasdkjfhksdjhfk", "ne");
        HttpEntity<Account> entity = new HttpEntity<>(accountToAdd, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/register",
                HttpMethod.POST, entity, String.class);

        String expected = "{\"error\":\"Ошибка валидации при регистрации аккаунта: size must be between 3 and 50  \"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    /**
     * Тестирование успешной активации аккаунта.
     */
    @Test
    public void accountActivationWhenSuccessfulTest() {
        Account account = new Account("some name", "some new log", "pass");
        accountService.add(account);

        assertFalse(accountDAO.getAccountByLogin("some new log").isActivated()); // аккаунт не активирован

        String code = accountService.getCodeForActivation();
        String url = "/api/activation?code=" + code;
        HttpEntity entity = new HttpEntity(headers);

        restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        assertTrue(accountDAO.getAccountByLogin("some new log").isActivated()); // проверяем статус активации аккаунта (активирован)
    }

    /**
     * Тестирование активации аккаунта, когда код активации не совпадаетс тем, что был выслан.
     */
    @Test
    public void accountActivationWhenCodeIsWrongTest() throws JSONException {
        HttpEntity entity = new HttpEntity(headers);
        String url = "/api/activation?code=someRandomString";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String expected = "{\"error\":\"Нет аккаунта с данным кодом активации: someRandomString\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    /**
     * Тестирование успешной проверки логина и пароля.
     */
    @Test
    public void loginWhenSuccessfulTest() throws JSONException {
        Account account = new Account("some name", "another log", "tricky pass");
        accountService.add(account);

        Map<String, String> map = new HashMap<>();
        map.put("login", "another log");
        map.put("password", "tricky pass");
        HttpEntity<Map> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/login",
                HttpMethod.POST, entity, String.class);

        String expected = "{\"data\":{\"result\":\"success\"}}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    /**
     * Тестирование проверки логина и пароля при вводе неправильного пароля.
     */
    @Test
    public void loginWhenPassIsWrongTest() throws JSONException {
        Account account = new Account("some name", "some log2", "tricky pass");
        accountService.add(account);

        Map<String, String> map = new HashMap<>();
        map.put("login", "some log2");
        map.put("password", "wrong pass");
        HttpEntity<Map> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/login",
                HttpMethod.POST, entity, String.class);

        String expected = "{\"error\":\"Password is wrong!\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    /**
     * Тестирование проверки логина и пароля при отсутствии пароля (или логина).
     */
    @Test
    public void loginWhenThereIsNoPasswordTest() throws JSONException {
        Map<String, String> map = new HashMap<>();
        map.put("login", "some log3");
        // no pass
        HttpEntity<Map> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/login",
                HttpMethod.POST, entity, String.class);

        String expected = "{\"error\":\"Отсутствует логин или пароль\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

}