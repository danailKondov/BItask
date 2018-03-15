package ru.bellintegrator.practice.orgs.controller;

import org.springframework.http.ResponseEntity;
import ru.bellintegrator.practice.orgs.view.CriteriaView;
import ru.bellintegrator.practice.orgs.model.Organisation;

/**
 * Created on 11.03.2018.
 */
public interface OrganisationController {

    ResponseEntity<?> getOrganisationById(long id);
    ResponseEntity<?> saveOrganisation(Organisation organisation);
    ResponseEntity<?> updateOrganisation(Organisation organisation);
    ResponseEntity getAllByCriteria(CriteriaView view);
    ResponseEntity deleteOrganisation(Organisation organisation);
}
