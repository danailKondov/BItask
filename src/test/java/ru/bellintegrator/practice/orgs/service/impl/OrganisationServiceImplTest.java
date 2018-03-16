package ru.bellintegrator.practice.orgs.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.practice.exceptionhandler.exceptions.OrganisationException;
import ru.bellintegrator.practice.orgs.dao.OrganisationRepository;
import ru.bellintegrator.practice.orgs.model.Organisation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created on 14.03.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrganisationServiceImplTest {

    @Mock
    private OrganisationRepository repository;

    @InjectMocks
    private OrganisationServiceImpl service;

    @Test
    public void getOrganisationByIdWhenSuccessfulTest() {
        Organisation organisation = new Organisation();
        when(repository.findOne(1L)).thenReturn(organisation);

        Organisation orgFromDB = service.getOrganisationById(1L);

        assertEquals(organisation, orgFromDB);
    }

    @Test (expected = OrganisationException.class)
    public void getOrganisationByIdWhenFailTest() {
        when(repository.findOne(1L)).thenReturn(null);
        service.getOrganisationById(1L);
    }

    @Test
    public void saveWhenSuccessfulTest() {
        Organisation organisation = new Organisation("someNewOrg",
                "Some Org ltd.", "542634745674", "322544987",
                "Samara, Mashinostroenia st, 27A", "89107997878", true);
        when(repository.findOrganisationByName(organisation.getName())).thenReturn(null);

        service.save(organisation);

        verify(repository).save(organisation);
    }

    @Test (expected = OrganisationException.class)
    public void saveWhenFailTest() {
        Organisation organisation = new Organisation("someNewOrg",
                "Some Org ltd.", "542634745674", "322544987",
                "Samara, Mashinostroenia st, 27A", "89107997878", true);
        when(repository.findOrganisationByName(organisation.getName())).thenReturn(organisation);
        service.save(organisation);
    }

    @Test
    public void updateWhenSuccessfulTest() {
        Organisation organisation = new Organisation("someNewOrg",
                "Some Org ltd.", "542634745674", "322544987",
                "Samara, Mashinostroenia st, 27A", "89107997878", true);
        organisation.setId(1L);
        Organisation orgToUpdate = new Organisation();
        when(repository.findOne(organisation.getId())).thenReturn(orgToUpdate);

        service.update(organisation);

        verify(repository).save(orgToUpdate);
    }

    @Test (expected = OrganisationException.class)
    public void updateWhenFailTest() {
        Organisation organisation = new Organisation("someNewOrg",
                "Some Org ltd.", "542634745674", "322544987",
                "Samara, Mashinostroenia st, 27A", "89107997878", true);
        organisation.setId(1L);
        when(repository.findOne(organisation.getId())).thenReturn(null);
        service.update(organisation);
    }
}