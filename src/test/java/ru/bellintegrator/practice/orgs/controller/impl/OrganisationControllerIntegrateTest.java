package ru.bellintegrator.practice.orgs.controller.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.orgs.dao.OrganisationRepository;
import ru.bellintegrator.practice.orgs.model.Organisation;
import ru.bellintegrator.practice.orgs.service.OrganisationService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created on 14.03.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class)
public class OrganisationControllerIntegrateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrganisationRepository repository;

    @Autowired
    private OrganisationService service;

    private HttpHeaders headers;

    @Before
    public void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        repository.deleteAll();
    }

    @Test
    public void getOrganisationByIdWhenSuccessfulTest() {
        Organisation organisation = new Organisation("someNewOrg",
                "Some Org ltd.", "542634745674", "322544987",
                "Samara, Mashinostroenia st, 27A", "89107997878", true);
        service.save(organisation);
        long id = repository.findOrganisationByName("someNewOrg").getId();
        String url = "/api/organisation/" + id;

        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String expected = "{\"data\":" +
                "{\"id\":"+ id + "," +
                "\"name\":\"someNewOrg\"," +
                "\"fullName\":\"Some Org ltd.\"," +
                "\"inn\":\"542634745674\"," +
                "\"kpp\":\"322544987\"," +
                "\"address\":\"Samara, Mashinostroenia st, 27A\"," +
                "\"phone\":\"89107997878\"," +
                "\"isActive\":true}}";
        String result = response.getBody();
        assertThat(result, is(expected));
    }

    @Test
    public void getOrganisationByIdWhenFailTest() {
        String url = "/api/organisation/100500";
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String expected = "{\"error\":\"Организиции с подобным ID нет в базе: 100500\"}";
        String result = response.getBody();
        assertThat(result, is(expected));
    }

    @Test
    public void saveOrganisationWhenSuccessfulTest() {
        Organisation organisation = new Organisation("someNewOrgSoNice",
                "Some Nice Org ltd.", "542456745674", "329874987",
                "Alushta, Mashinostroenia st, 27A", "89207997878", true);
        HttpEntity entity = new HttpEntity<>(organisation, headers);
        String url = "/api/organisation/save";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String expected = "{\"data\":{\"result\":\"success\"}}";
        String result = response.getBody();
        assertThat(result, is(expected));
        assertNotNull(repository.findOrganisationByName("someNewOrgSoNice"));
    }

    @Test
    public void saveOrganisationWhenFailTest() {
        Organisation orgInDB = new Organisation("someNewOrgSoNiceAndSweet",
                "Some Nice Sweet Org ltd.", "542456745674", "329874987",
                "Penza, Lenina st, 27A", "89207997878", true);
        service.save(orgInDB);
        Organisation orgToAdd = new Organisation("someNewOrgSoNiceAndSweet",
                "Some Nice Org ltd.", "542456745674", "329874987",
                "Alushta, Mashinostroenia st, 27A", "89207997878", true);
        HttpEntity entity = new HttpEntity<>(orgToAdd, headers);
        String url = "/api/organisation/save";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String expected = "{\"error\":" +
                "\"Невозможно создание новой организации - организация с подобным именем уже есть в базе: " +
                "someNewOrgSoNiceAndSweet\"}";
        String result = response.getBody();
        assertThat(result, is(expected));
    }

    @Test
    public void updateOrganisationWhenSuccessfulTest() {
        Organisation orgInDB = new Organisation("someNotSoNewOrg",
                "Some Org ltd.", "542634745674", "322544987",
                "Samara, Mashinostroenia st, 27A", "89107997878", true);
        service.save(orgInDB);
        long id = repository.findOrganisationByName("someNotSoNewOrg").getId();
        Organisation orgToUpdate = new Organisation("someNewOrgSoNiceAndSweet",
                "Some Nice Org ltd.", "542456745674", "329874987",
                "Alushta, Mashinostroenia st, 27A", "89207997878", true);
        orgToUpdate.setId(id);
        String url = "/api/organisation/update";
        HttpEntity entity = new HttpEntity<>(orgToUpdate, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String expected = "{\"data\":{\"result\":\"success\"}}";
        String result = response.getBody();
        assertThat(result, is(expected));
        assertEquals(repository.findOne(id),orgToUpdate);
    }

    @Test
    public void updateOrganisationWhenFailTest() {
        Organisation orgToUpdate = new Organisation("someNewOrgSoNiceAndSweet",
                "Some Nice Org ltd.", "542456745674", "329874987",
                "Alushta, Mashinostroenia st, 27A", "89207997878", true);
        orgToUpdate.setId(100500);
        String url = "/api/organisation/update";
        HttpEntity entity = new HttpEntity<>(orgToUpdate, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String expected = "{\"error\":\"Невозможно обновить - организации с подобным ID нет в базе: 100500\"}";
        String result = response.getBody();
        assertThat(result, is(expected));
        assertNull(repository.findOne(100500L));
    }

    @Test
    public void getAllByCriteriaWhenOneCriteriaTest() {
        addToDBSomeOrganisations();

//        CriteriaView view = new CriteriaView(null, "542786545674", null);
        // isActive null = "false", причем из Postman все ок, проблема где-то в объекте

        String body = "{\"inn\" : \"542786545674\"}";
        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/organisation/list";
        long id = repository.findOrganisationByName("White").getId();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String expected = "{\"data\":" +
                "[{\"id\":" + id + "," +
                "\"name\":\"White\"," +
                "\"isActive\":true}]}";
        String result = response.getBody();
        assertThat(result, is(expected));
    }

    private void addToDBSomeOrganisations() {
        Organisation orgInDB = new Organisation("White",
                "White ltd.", "542786545674", "324544987",
                "Samara, Mashinostroenia st, 27A", "89107997878", true);
        service.save(orgInDB);
        Organisation orgInDB2 = new Organisation("Black",
                "Black ltd.", "786534327874", "322145987",
                "Samara, Mashinostroenia st, 27A", "89107997878", false);
        service.save(orgInDB2);
        Organisation orgInDB3 = new Organisation("Blue Org",
                "Blue Org ltd.", "986534745674", "322544987",
                "Samara, Mashinostroenia st, 27A", "89107997878", false);
        service.save(orgInDB3);
        Organisation orgInDB4 = new Organisation("New Org",
                "New Org ltd.", "125434745674", "322544987",
                "Samara, Mashinostroenia st, 27A", "89107997878", true);
        service.save(orgInDB4);
        Organisation orgInDB5 = new Organisation("Pretty Org",
                "Pretty Org ltd.", "987434745674", "322544987",
                "Samara, Mashinostroenia st, 27A", "89107997878", true);
        service.save(orgInDB5);
    }

    @Test
    public void getAllByCriteriaWhenTwoCriteriaTest() {
        addToDBSomeOrganisations();

        String body = "{\"name\" : \"Org\", \"isActive\" : \"true\"}";
        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/organisation/list";

        long id1 = repository.findOrganisationByName("New Org").getId();
        long id2 = repository.findOrganisationByName("Pretty Org").getId();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String expected = "{\"data\":" +
                "[{\"id\":" + id1 + "," +
                "\"name\":\"New Org\"," +
                "\"isActive\":true}," +
                "{\"id\":" + id2 + "," +
                "\"name\":\"Pretty Org\"," +
                "\"isActive\":true}]}";
        String result = response.getBody();
        assertThat(result, is(expected));
    }

    @Test
    public void getAllByCriteriaWhenThreeCriteriaTest() {
        addToDBSomeOrganisations();

        String body = "{\"name\" : \"Org\", \"inn\": \"987434745674\", \"isActive\" : \"true\"}";
        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/organisation/list";

        long id = repository.findOrganisationByName("Pretty Org").getId();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String expected = "{\"data\":" +
                "[{\"id\":" + id + "," +
                "\"name\":\"Pretty Org\"," +
                "\"isActive\":true}]}";
        String result = response.getBody();
        assertThat(result, is(expected));
    }

    @Test
    public void getAllByCriteriaWhenNoCriteriaTest() {
        addToDBSomeOrganisations();

        long id1 = repository.findOrganisationByName("White").getId();
        long id2 = repository.findOrganisationByName("Black").getId();
        long id3 = repository.findOrganisationByName("Blue Org").getId();
        long id4 = repository.findOrganisationByName("New Org").getId();
        long id5 = repository.findOrganisationByName("Pretty Org").getId();

        String body = "{}";
        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/organisation/list";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String expected = "{\"data\":" +
                "[{\"id\":" + id1 + "," +
                "\"name\":\"White\"," +
                "\"isActive\":true}," +
                "{\"id\":" + id2 + "," +
                "\"name\":\"Black\"," +
                "\"isActive\":false}," +
                "{\"id\":" + id3 + "," +
                "\"name\":\"Blue Org\"," +
                "\"isActive\":false}," +
                "{\"id\":" + id4 + "," +
                "\"name\":\"New Org\"," +
                "\"isActive\":true}," +
                "{\"id\":" + id5 + "," +
                "\"name\":\"Pretty Org\"," +
                "\"isActive\":true}]}";
        String result = response.getBody();
        assertThat(result, is(expected));
    }

    @Test
    public void deleteByIdTest() {
        addToDBSomeOrganisations();
        long id = repository.findOrganisationByName("White").getId();

        String body = "{\"id\" : " + id + "}";
        HttpEntity entity = new HttpEntity<>(body, headers);
        String url = "/api/organisation/delete";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String expected = "{\"data\":{\"result\":\"success\"}}";
        String result = response.getBody();
        assertThat(result, is(expected));
        assertNull(repository.findOrganisationByName("White"));
    }
}