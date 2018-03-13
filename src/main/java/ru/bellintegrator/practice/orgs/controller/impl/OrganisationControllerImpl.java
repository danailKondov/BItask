package ru.bellintegrator.practice.orgs.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.Utils.CustomDataOut;
import ru.bellintegrator.practice.Utils.CustomSuccessResponse;
import ru.bellintegrator.practice.orgs.controller.OrganisationController;
import ru.bellintegrator.practice.orgs.model.Organisation;
import ru.bellintegrator.practice.orgs.service.OrganisationService;
import ru.bellintegrator.practice.orgs.view.CriteriaView;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created on 11.03.2018.
 */
@RestController
@RequestMapping(value = "/api/organisation", produces = APPLICATION_JSON_VALUE)
public class OrganisationControllerImpl implements OrganisationController {

    private OrganisationService service;

    @Autowired
    public OrganisationControllerImpl(OrganisationService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomDataOut<Organisation>> getOrganisationById(@PathVariable long id) {
        Organisation organisation = service.getOrganisationById(id);
        CustomDataOut<Organisation> dataOut = new CustomDataOut<>(organisation);
        return new ResponseEntity<>(dataOut, HttpStatus.FOUND);
    }

    @Override
    @PostMapping(value = "/save")
    public ResponseEntity saveOrganisation(@RequestBody @Valid Organisation organisation) {
        service.save(organisation);
        return new ResponseEntity<>(new CustomSuccessResponse(), HttpStatus.CREATED);
    }

    @Override
    @PostMapping(value = "/update")
    public ResponseEntity updateOrganisation(@RequestBody @Valid Organisation organisation) {
        service.update(organisation);
        return new ResponseEntity<>(new CustomSuccessResponse(), HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/list")
    public ResponseEntity getAllByCriteria(@RequestBody CriteriaView view) {
        List<Organisation> list = service.getOrganisationsByCriteria(view.getName(), view.getInn(), view.isActive());
        CustomDataOut<List<Organisation>> dataOut = new CustomDataOut<>(list);
        return new ResponseEntity<>(dataOut, HttpStatus.FOUND);
    }

}
