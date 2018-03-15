package ru.bellintegrator.practice.office.controller;

import org.springframework.http.ResponseEntity;

/**
 * Created on 15.03.2018.
 */
public interface OfficeController {

    ResponseEntity<?> getOfficeById(long id);
}
