package ru.bellintegrator.practice.referencebook.controller;


import org.springframework.http.ResponseEntity;

/**
 * Created on 21.03.2018.
 */
public interface ReferenceBookController {

    ResponseEntity getAllDocs();
    ResponseEntity getAllCountries();
}
