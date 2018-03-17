package ru.bellintegrator.practice.office.controller;

import org.springframework.http.ResponseEntity;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.view.OfficeView;

/**
 * Created on 15.03.2018.
 */
public interface OfficeController {

    ResponseEntity<?> getOfficeById(long id);
    ResponseEntity updateOffice(Office office);
    ResponseEntity deleteOffice(Office office);
    ResponseEntity saveOffice(OfficeView view);
    ResponseEntity getAllByCriteria(OfficeView view);
}
