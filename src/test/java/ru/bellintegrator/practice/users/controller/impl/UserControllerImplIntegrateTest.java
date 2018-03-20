package ru.bellintegrator.practice.users.controller.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.referencebook.model.Country;
import ru.bellintegrator.practice.referencebook.model.Document;
import ru.bellintegrator.practice.users.dao.UserRepository;
import ru.bellintegrator.practice.users.model.User;
import ru.bellintegrator.practice.users.view.UserDto;

import java.util.Date;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created on 20.03.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class)
public class UserControllerImplIntegrateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private HttpHeaders headers;

    @Before
    public void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        userRepository.deleteAll();
    }

    @Test
    public void getUserByIdWhenSuccessfulTest() {
        User user = addOneUserToDB();
        Long id = getIdFromUserInDB(user);
        String url = "/api/user/" + id;
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String expected = "{\"data\":" +
                "{\"id\":" + id + "," +
                "\"firstName\":\"firstName\"," +
                "\"secondName\":\"lastName\"," +
                "\"middleName\":\"middleName\"," +
                "\"position\":\"position\"," +
                "\"phone\":\"123456789\"," +
                "\"docName\":\"Паспорт гражданина Российской Федерации\"," +
                "\"docCode\":21," +
                "\"docNumber\":\"568741\"," +
                "\"docDate\":\"2018-03-20\"," +
                "\"citizenshipName\":\"Российская Федерация\"," +
                "\"citizenshipCode\":643," +
                "\"isIdentified\":true}}";
        String result = response.getBody();
        assertThat(result, is(expected));
    }

    private Long getIdFromUserInDB(User user) {
        Long id = null;
        Iterable<User> iterable = userRepository.findAll();
        Iterator<User> iterator = iterable.iterator();
        while(iterator.hasNext()) {
            User userFromDb = iterator.next();
            if (userFromDb.equals(user)) {
                id = userFromDb.getId();
                break;
            }
        }
        return id;
    }

    @Test
    public void getUserByIdWhenFailTest() {
        String url = "/api/user/100500";
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String expected = "{\"error\":\"В базе данных нет сотрудника с подобным ID: 100500\"}";
        String result = response.getBody();
        assertThat(result, is(expected));
    }

    @Test
    public void updateUserWhenSuccessfulTest() {
        User user = addOneUserToDB();
        Long id = getIdFromUserInDB(user);

        String body = "{\"id\" : " + id + "," +
                "  \"firstName\" : \"updateName\"," +
                "  \"secondName\" : \"updateSecName\"," +
                "  \"middleName\": \"upMidName\"," +
                "  \"position\" : \"some position\"," +
                "  \"phone\" : \"89057985212\"," +
                "  \"docName\" : \"Паспорт иностранного гражданина\"," +
                "  \"docNumber\" : \"123456\"," +
                "  \"docDate\" : \"2010-05-10\"," +
                "  \"citizenshipName\" : \"Республика Гвинея-Бисау\"," +
                "  \"citizenshipCode\" : 624," +
                "  \"isIdentified\" : \"true\"}";

        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/user/update";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"data\":{\"result\":\"success\"}}";
        assertThat(result, is(expected));
        assertThat(userRepository.findOne(id).getFirstName(), is("updateName"));
    }

    private User addOneUserToDB() {
        Office office = new Office();
        office.setId(1L);
        Document document = new Document();
        document.setCode(21L);
        Country country = new Country();
        country.setCode(643L);
        User user = new User(office,
                "firstName",
                "lastName",
                "middleName",
                "position",
                "123456789",
                document,
                "568741",
                new Date(1521545817741L), // 2018-03-20
                country,
                true);
        userRepository.save(user);
        return user;
    }

    @Test
    public void updateUserWhenFailTest() {
        String body = "{\"id\" : 100500," +
                "  \"firstName\" : \"updateName\"," +
                "  \"secondName\" : \"updateSecName\"," +
                "  \"middleName\": \"upMidName\"," +
                "  \"position\" : \"some position\"," +
                "  \"phone\" : \"89057985212\"," +
                "  \"docName\" : \"Паспорт иностранного гражданина\"," +
                "  \"docNumber\" : \"123456\"," +
                "  \"docDate\" : \"2010-05-10\"," +
                "  \"citizenshipName\" : \"Республика Гвинея-Бисау\"," +
                "  \"citizenshipCode\" : 624," +
                "  \"isIdentified\" : \"true\"}";

        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/user/update";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"error\":\"Невозможно провести обновление - пользователь с таким ID отсутсвует в базе данных.\"}";
        assertThat(result, is(expected));
        assertNull(userRepository.findOne(100500L));
    }

    @Test
    public void updateUserWhenDocNameIsWrongTest() {
        User user = addOneUserToDB();
        Long id = getIdFromUserInDB(user);

        String body = "{\"id\" : " + id + "," +
                "  \"firstName\" : \"updateName\"," +
                "  \"secondName\" : \"updateSecName\"," +
                "  \"middleName\": \"upMidName\"," +
                "  \"position\" : \"some position\"," +
                "  \"phone\" : \"89057985212\"," +
                "  \"docName\" : \"Нет такого документа\"," +
                "  \"docNumber\" : \"123456\"," +
                "  \"docDate\" : \"2010-05-10\"," +
                "  \"citizenshipName\" : \"Республика Гвинея-Бисау\"," +
                "  \"citizenshipCode\" : 624," +
                "  \"isIdentified\" : \"true\"}";

        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/user/update";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"error\":\"Невозможно провести обновление или сохранение пользователя - " +
                "в базе данных отсутсвует документ с таким именем: Нет такого документа\"}";
        assertThat(result, is(expected));
    }

    @Test
    public void updateUserWhenCitizenshipCodeIsWrongTest() {
        User user = addOneUserToDB();
        Long id = getIdFromUserInDB(user);

        String body = "{\"id\" : " + id + "," +
                "  \"firstName\" : \"updateName\"," +
                "  \"secondName\" : \"updateSecName\"," +
                "  \"middleName\": \"upMidName\"," +
                "  \"position\" : \"some position\"," +
                "  \"phone\" : \"89057985212\"," +
                "  \"docName\" : \"Паспорт иностранного гражданина\"," +
                "  \"docNumber\" : \"123456\"," +
                "  \"docDate\" : \"2010-05-10\"," +
                "  \"citizenshipName\" : \"Республика Гвинея-Бисау\"," +
                "  \"citizenshipCode\" : 100500," +
                "  \"isIdentified\" : \"true\"}";

        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/user/update";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"error\":\"Невозможно провести обновление или сохранение пользователя - в базе данных отсутсвует страна с подобным кодом: 100500\"}";
        assertThat(result, is(expected));
    }

    @Test
    public void updateUserWhenCitizenshipNameIsWrongTest() {
        User user = addOneUserToDB();
        Long id = getIdFromUserInDB(user);

        String body = "{\"id\" : " + id + "," +
                "  \"firstName\" : \"updateName\"," +
                "  \"secondName\" : \"updateSecName\"," +
                "  \"middleName\": \"upMidName\"," +
                "  \"position\" : \"some position\"," +
                "  \"phone\" : \"89057985212\"," +
                "  \"docName\" : \"Паспорт иностранного гражданина\"," +
                "  \"docNumber\" : \"123456\"," +
                "  \"docDate\" : \"2010-05-10\"," +
                "  \"citizenshipName\" : \"Республика Неверлэнд\"," +
                "  \"citizenshipCode\" : 624," +
                "  \"isIdentified\" : \"true\"}";

        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/user/update";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"error\":\"Невозможно провести обновление или сохранение пользователя - " +
                "код страны (624) не соответствует названию (Республика Неверлэнд). " +
                "Данному коду соответствует: Республика Гвинея-Бисау\"}";
        assertThat(result, is(expected));
    }

    @Test
    public void deleteUserWhenSuccessfulTest() {
        User user = addOneUserToDB();
        Long id = getIdFromUserInDB(user);

        String body = "{\"id\" : " + id + "}";

        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/user/delete";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"data\":{\"result\":\"success\"}}";
        assertThat(result, is(expected));
        assertNull(userRepository.findOne(id));
    }

    @Test
    public void deleteUserWhenFailTest() {
        String body = "{\"id\" : 100500}";

        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/user/delete";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"error\":\"No class ru.bellintegrator.practice.users.model.User entity with id 100500 exists!\"}";
        assertThat(result, is(expected));
    }

    @Test
    public void saveUserWhenSuccessfulTest() {
        String body = "{\"officeId\" : 1," +
                "  \"firstName\" : \"saveName\"," +
                "  \"secondName\" : \"saveSecName\"," +
                "  \"middleName\": \"saveMidName\"," +
                "  \"position\" : \"some position\"," +
                "  \"phone\" : \"89057985212\"," +
                "  \"docName\" : \"Паспорт иностранного гражданина\"," +
                "  \"docNumber\" : \"123456\"," +
                "  \"docDate\" : \"2010-05-10\"," +
                "  \"citizenshipName\" : \"Республика Гвинея-Бисау\"," +
                "  \"citizenshipCode\" : 624," +
                "  \"isIdentified\" : \"true\"" +
                "}";

        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/user/save";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"data\":{\"result\":\"success\"}}";
        assertThat(result, is(expected));

        boolean userWasSuccessfullySaved = false;
        Iterable<User> iterable = userRepository.findAll();
        Iterator<User> iterator = iterable.iterator();
        while(iterator.hasNext()) {
            User userFromDb = iterator.next();
            if ("saveName".equals(userFromDb.getFirstName()) && "saveMidName".equals(userFromDb.getMiddleName())) {
                userWasSuccessfullySaved = true;
                break;
            }
        }
        assertTrue(userWasSuccessfullySaved);
    }

    @Test
    public void saveUserWhenNoOfficeIdTest() {
        String body = "{" + // no officeId
                "  \"firstName\" : \"saveName\"," +
                "  \"secondName\" : \"saveSecName\"," +
                "  \"middleName\": \"saveMidName\"," +
                "  \"position\" : \"some position\"," +
                "  \"phone\" : \"89057985212\"," +
                "  \"docName\" : \"Паспорт иностранного гражданина\"," +
                "  \"docNumber\" : \"123456\"," +
                "  \"docDate\" : \"2010-05-10\"," +
                "  \"citizenshipName\" : \"Республика Гвинея-Бисау\"," +
                "  \"citizenshipCode\" : 624," +
                "  \"isIdentified\" : \"true\"" +
                "}";

        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/user/save";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"error\":\"Невозможно сохранить служащего без ID его офиса\"}";
        assertThat(result, is(expected));
//        System.out.println(result);
    }

    @Test
    public void getAllUsersByCriteriasTest() {}
}