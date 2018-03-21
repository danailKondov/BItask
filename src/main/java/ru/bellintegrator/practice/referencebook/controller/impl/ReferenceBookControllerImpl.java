package ru.bellintegrator.practice.referencebook.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.practice.referencebook.controller.ReferenceBookController;
import ru.bellintegrator.practice.referencebook.model.Country;
import ru.bellintegrator.practice.referencebook.model.Document;
import ru.bellintegrator.practice.referencebook.service.ReferenceBookService;
import ru.bellintegrator.practice.utils.CustomDataOut;

import java.util.List;

/**
 * Created on 21.03.2018.
 */
@RestController
@RequestMapping(value = "/api")
public class ReferenceBookControllerImpl implements ReferenceBookController {

    private ReferenceBookService service;

    @Autowired
    public ReferenceBookControllerImpl(ReferenceBookService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "/docs")
    public ResponseEntity getAllDocs() {
        List<Document> docs = service.getAllDocs();
        CustomDataOut dataOut = new CustomDataOut<>(docs);
        return new ResponseEntity<>(dataOut, HttpStatus.FOUND);
    }

    @Override
    @GetMapping(value = "/countries")
    public ResponseEntity getAllCountries() {
        List<Country> countries = service.getAllCountries();
        CustomDataOut dataOut = new CustomDataOut<>(countries);
        return new ResponseEntity<>(dataOut, HttpStatus.FOUND);
    }
}
