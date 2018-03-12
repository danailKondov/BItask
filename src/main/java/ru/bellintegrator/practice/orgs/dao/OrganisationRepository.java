package ru.bellintegrator.practice.orgs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.bellintegrator.practice.orgs.model.Organisation;

import java.util.List;

/**
 * Класс для доступа к БД.
 *
 * Created on 11.03.2018.
 *
 * The JpaSpecificationExecutor<T> interface declares the methods that can
 * be used to invoke database queries that use the JPA Criteria API.
 */
public interface OrganisationRepository extends CrudRepository<Organisation, Long>, JpaSpecificationExecutor<Organisation> {

    Organisation findOrganisationByName(String name);
}
