package ru.bellintegrator.practice.orgs.dao;

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
import ru.bellintegrator.practice.orgs.model.Organisation;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created on 14.03.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class OrganisationRepositoryTest {

    @Autowired
    private OrganisationRepository repository;

    /**
     * Тестирует сохранение и получение организации по имени.
     */
    @Test
    public void saveAndFindByNameTest() {
        Organisation organisation = new Organisation("someOrg",
                "Some Org ltd.", "542634745674", "322544987",
                "Samara, Mashinostroenia st, 27A", "89107997878", true);
        repository.save(organisation);

        Organisation organisation1 = repository.findOrganisationByName("someOrg");
        assertTrue(organisation.equals(organisation1));
    }
}