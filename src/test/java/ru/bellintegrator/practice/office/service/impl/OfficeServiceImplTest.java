package ru.bellintegrator.practice.office.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.practice.exceptionhandler.exceptions.OfficeException;
import ru.bellintegrator.practice.office.dao.OfficeRepository;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.orgs.dao.OrganisationRepository;
import ru.bellintegrator.practice.orgs.model.Organisation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created on 17.03.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class OfficeServiceImplTest {

    @Mock
    private OfficeRepository officeRepository;

    @Mock
    private OrganisationRepository organisationRepository;

    @InjectMocks
    private OfficeServiceImpl service;

    @Test
    public void getOfficeByIdWhenSuccessfulTest() {
        Office office = new Office();
        long id = 1L;
        when(officeRepository.findOne(id)).thenReturn(office);

        Office expected = service.getOfficeById(id);

        assertThat(office, is(expected));
    }

    @Test(expected = OfficeException.class)
    public void getOfficeByIdWhenFailTest() {
        long id = 1L;
        when(officeRepository.findOne(id)).thenReturn(null);
        service.getOfficeById(id);
    }

    @Test
    public void updateOfficeWhenSuccessfulTest() {
        Office office = new Office();
        long id = 1L;
        office.setId(id);
        when(officeRepository.findOne(id)).thenReturn(office);

        service.updateOffice(office);

        verify(officeRepository).save(office);
    }

    @Test(expected = OfficeException.class)
    public void updateOfficeWhenFailTest() {
        Office office = new Office(); // no id
        service.updateOffice(office);
    }

    @Test
    public void deleteOfficeWhenSuccessfulTest() {
        Office office = new Office();
        long id = 1L;
        office.setId(id);

        service.deleteOffice(office);

        verify(officeRepository).delete(id);
    }

    @Test(expected = OfficeException.class)
    public void deleteOfficeWhenFailTest() {
        Office office = new Office();
        service.deleteOffice(office);
    }

    @Test
    public void saveOfficeWhenSuccessfulTest() {
        Long orgId = 1L;
        Office office = new Office();
        office.setOrgId(orgId);
        Organisation organisation = new Organisation();
        when(organisationRepository.findOne(orgId)).thenReturn(organisation);

        officeRepository.save(office);

        verify(officeRepository).save(office);
    }

    @Test(expected = OfficeException.class)
    public void saveOfficeWhenFailTest() {
        Long orgId = 1L;
        Office office = new Office();
        office.setOrgId(orgId);
        when(organisationRepository.findOne(orgId)).thenReturn(null);

        service.saveOffice(office);
    }
}