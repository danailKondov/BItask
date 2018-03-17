package ru.bellintegrator.practice.office.controller.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.office.dao.OfficeRepository;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.service.OfficeService;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.orgs.model.Organisation;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created on 17.03.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class)
public class OfficeControllerImplIntegateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private OfficeService officeService;

    private HttpHeaders headers;

    @Before
    public void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        officeRepository.deleteAll();
    }

    @Test
    public void getOfficeByIdWhenSuccessfulTest() {
        Office office = new Office("officeName", "officeAddress", "89057456321", true);
        Organisation organisation = new Organisation();
        organisation.setId(2L);
        office.setOrganisation(organisation);
        officeRepository.save(office);
        Long id = getOfficeIdFromDB(office);
        String url = "/api/office/" + id;

        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String result = response.getBody();
        String expected = "{\"data\":" +
                "{\"id\":" + id + "," +
                "\"name\":\"officeName\"," +
                "\"address\":\"officeAddress\"," +
                "\"phone\":\"89057456321\"," +
                "\"isActive\":true}}";
        assertThat(result, is(expected));
    }

    private Long getOfficeIdFromDB(Office office) {
        Iterable<Office> iterable = officeRepository.findAll();
        Iterator<Office> iterator = iterable.iterator();
        Long id = null;
        while(iterator.hasNext()) {
            Office officeInDB = iterator.next();
            if (office.equals(officeInDB)) id = officeInDB.getId();
        }
        return id;
    }

    @Test
    public void getOfficeByIdWhenFailTest() {
        String url = "/api/office/100500";

        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String result = response.getBody();
        String expected = "{\"error\":\"В базе нет офиса с подобным ID: 100500\"}";
        assertThat(result, is(expected));
    }

    @Test
    public void updateOfficeWhenSuccessfulTest() {
        Office office = new Office("officeName", "officeAddress", "89057456321", true);
        Organisation organisation = new Organisation();
        organisation.setId(2L);
        office.setOrganisation(organisation);
        officeRepository.save(office);
        Long id = getOfficeIdFromDB(office);
        Office updatedOffice = new Office("updatedName", "updatedAddress", "891011122233", false);
        updatedOffice.setId(id);
        String url = "/api/office/update";
        HttpEntity entity = new HttpEntity<>(updatedOffice, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"data\":{\"result\":\"success\"}}";
        assertThat(result, is(expected));
        assertEquals(officeRepository.findOne(id), updatedOffice);
    }

    @Test
    public void updateOfficeWhenFailTest() {
        Office updatedOffice = new Office("updatedName", "updatedAddress", "891011122233", false);
        updatedOffice.setId(100500L);
        String url = "/api/office/update";
        HttpEntity entity = new HttpEntity<>(updatedOffice, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"error\":\"Невозможно обновить, поскольку в базе нет офиса с подобным ID: 100500\"}";
        assertThat(result, is(expected));
    }

    @Test
    public void deleteOfficeWhenSuccessfulTest() {
        Office office = new Office("officeName", "officeAddress", "89057456321", true);
        Organisation organisation = new Organisation();
        organisation.setId(2L);
        office.setOrganisation(organisation);
        officeRepository.save(office);
        Long id = getOfficeIdFromDB(office);
        String url = "/api/office/delete";
        String body = "{\"id\" : " + id + "}";
        HttpEntity entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"data\":{\"result\":\"success\"}}";
        assertThat(result, is(expected));
        assertNull(officeRepository.findOne(id));
    }

    @Test
    public void deleteOfficeWhenFailTest() {
        String url = "/api/office/delete";
        String body = "{\"id\" : 100500}";
        HttpEntity entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"error\":\"No class ru.bellintegrator.practice.office.model.Office entity with id 100500 exists!\"}";
        assertThat(result, is(expected));
    }

    @Test
    public void saveOfficeWhenSuccessfulTest() {
        String url = "/api/office/save";
        // тест предполагает, что база инициализирована data.sql
        String body = "{" +
                "  \"orgId\" : 1,\n" +
                "  \"name\" : \"office save test\",\n" +
                "  \"address\" : \"somewhere\",\n" +
                "  \"phone\" : \"89054563278\",\n" +
                "  \"isActive\" : \"true\"\n" +
                "}";
        HttpEntity entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"data\":{\"result\":\"success\"}}";
        assertThat(result, is(expected));

        Office office = new Office("office save test", "somewhere", "89054563278", true);
        Long id = getOfficeIdFromDB(office);
        assertNotNull(id); // office in DB
    }

    @Test
    public void saveOfficeWhenFailTest() {
        String url = "/api/office/save";
        String body = "{" +
                "  \"name\" : \"office save test\",\n" +
                "  \"address\" : \"somewhere\",\n" +
                "  \"phone\" : \"89054563278\",\n" +
                "  \"isActive\" : \"true\"\n" +
                "}";
        HttpEntity entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"error\":\"Невозможно сохранить офис без ID организации\"}";
        assertThat(result, is(expected));

        Office office = new Office("office save test", "somewhere", "89054563278", true);
        Long id = getOfficeIdFromDB(office);
        assertNull(id); // office not in DB
    }

    @Test
    public void findAllByCriteriaWhenOneTest() {
        Office office1 = new Office("Name1", "Address1", "89057445321", true);
        Organisation organisation = new Organisation();
        organisation.setId(2L);
        office1.setOrganisation(organisation);
        officeRepository.save(office1);
        Long id1 = getOfficeIdFromDB(office1);

        Office office2 = new Office("Name2", "Address2", "89057456321", true);
        office2.setOrganisation(organisation);
        officeRepository.save(office2);
        Long id2 = getOfficeIdFromDB(office2);

        Office office3 = new Office("Name3", "Address3", "89089478321", false);
        office3.setOrganisation(organisation);
        officeRepository.save(office3);

        Office office4 = new Office("Name4", "Address4", "89057876521", false);
        office4.setOrganisation(organisation);
        officeRepository.save(office4);

        String url = "/api/office/list";
        String body = "{\"isActive\" : \"true\"}";
        HttpEntity entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"data\":" +
                "[{\"id\":" + id1 + "," +
                "\"name\":\"Name1\"," +
                "\"isActive\":true}," +
                "{\"id\":" + id2 + "," +
                "\"name\":\"Name2\"," +
                "\"isActive\":true}]}";
        assertThat(result, is(expected));
    }

    @Test
    public void findAllByCriteriaWhenTwoTest() {
        Office office1 = new Office("Name1", "Address1", "89057445321", true);
        Organisation organisation = new Organisation();
        organisation.setId(2L);
        office1.setOrganisation(organisation);
        officeRepository.save(office1);
        Long id1 = getOfficeIdFromDB(office1);

        Office office2 = new Office("Name2", "Address2", "89057456321", true);
        office2.setOrganisation(organisation);
        officeRepository.save(office2);
        Long id2 = getOfficeIdFromDB(office2);

        Office office3 = new Office("Name3", "Address3", "89089478321", false);
        office3.setOrganisation(organisation);
        officeRepository.save(office3);

        Office office4 = new Office("Name4", "Address4", "89057876521", false);
        office4.setOrganisation(organisation);
        officeRepository.save(office4);

        String url = "/api/office/list";
        String body = "{\"isActive\" : \"true\"," +
                "\"name\" : \"Name2\"}";
        HttpEntity entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"data\":" +
                "[{\"id\":" + id2 + "," +
                "\"name\":\"Name2\"," +
                "\"isActive\":true}]}";
        assertThat(result, is(expected));
    }

    @Test
    public void findAllByCriteriaWhenNoTest() {
        Office office1 = new Office("Name1", "Address1", "89057445321", true);
        Organisation organisation = new Organisation();
        organisation.setId(2L);
        office1.setOrganisation(organisation);
        officeRepository.save(office1);
        Long id1 = getOfficeIdFromDB(office1);

        Office office2 = new Office("Name2", "Address2", "89057456321", true);
        office2.setOrganisation(organisation);
        officeRepository.save(office2);
        Long id2 = getOfficeIdFromDB(office2);

        Office office3 = new Office("Name3", "Address3", "89089478321", false);
        office3.setOrganisation(organisation);
        officeRepository.save(office3);
        Long id3 = getOfficeIdFromDB(office3);

        Office office4 = new Office("Name4", "Address4", "89057876521", false);
        office4.setOrganisation(organisation);
        officeRepository.save(office4);
        Long id4 = getOfficeIdFromDB(office4);

        String url = "/api/office/list";
        String body = "{}";
        HttpEntity entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String result = response.getBody();
        String expected = "{\"data\":" +
                "[{\"id\":" + id1 + "," +
                "\"name\":\"Name1\"," +
                "\"isActive\":true}," +
                "{\"id\":" + id2 + "," +
                "\"name\":\"Name2\"," +
                "\"isActive\":true}," +
                "{\"id\":" + id3 + "," +
                "\"name\":\"Name3\"," +
                "\"isActive\":false}," +
                "{\"id\":" + id4 + "," +
                "\"name\":\"Name4\"," +
                "\"isActive\":false}]}";
        assertThat(result, is(expected));
    }
}