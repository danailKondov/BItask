package ru.bellintegrator.practice.orgs.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.bellintegrator.practice.orgs.model.Organisation;

import java.util.List;

/**
 * Created on 11.03.2018.
 */
public interface OrganisationRepository extends CrudRepository<Organisation, Long> {

    List<Organisation> findOrganisationsByIsActiveIsTrue();
    Organisation findOrganisationByIsActiveIsTrueAndId(long id);
    Organisation findOrganisationByName(String name);
}
