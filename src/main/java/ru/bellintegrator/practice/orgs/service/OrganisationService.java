package ru.bellintegrator.practice.orgs.service;

import org.springframework.stereotype.Service;
import ru.bellintegrator.practice.orgs.model.Organisation;

import java.util.List;

/**
 * Created on 11.03.2018.
 */
public interface OrganisationService {

    Organisation getOrganisationById(long id);
    void save(Organisation organisation);
    void update(Organisation organisation);
    List<Organisation> getOrganisationsByCriteria(String name, String inn, Boolean active);
}
