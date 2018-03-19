package ru.bellintegrator.practice.orgs.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.utils.CustomDataOut;
import ru.bellintegrator.practice.utils.CustomSuccessResponse;
import ru.bellintegrator.practice.orgs.controller.OrganisationController;
import ru.bellintegrator.practice.orgs.model.Organisation;
import ru.bellintegrator.practice.orgs.service.OrganisationService;
import ru.bellintegrator.practice.orgs.view.CriteriaView;
import ru.bellintegrator.practice.orgs.view.OrgDTO;

import javax.validation.Valid;

import java.util.List;

/**
 * Created on 11.03.2018.
 */
@RestController
@RequestMapping(value = "/api/organisation")
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
        List<OrgDTO> list = service.getOrganisationsByCriteria(view.getName(), view.getInn(), view.isActive());
        CustomDataOut<List<OrgDTO>> dataOut = new CustomDataOut<>(list);
        return new ResponseEntity<>(dataOut, HttpStatus.FOUND);
    }

    @Override
    @PostMapping(value = "/delete")
    public ResponseEntity deleteOrganisation(@RequestBody Organisation organisation) {
        long id = organisation.getId();
        service.deleteOrganisationById(id);
        return new ResponseEntity<>(new CustomSuccessResponse(), HttpStatus.OK);
    }

}
