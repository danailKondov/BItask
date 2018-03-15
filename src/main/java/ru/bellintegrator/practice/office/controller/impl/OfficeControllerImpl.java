package ru.bellintegrator.practice.office.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.Utils.CustomDataOut;
import ru.bellintegrator.practice.Utils.CustomSuccessResponse;
import ru.bellintegrator.practice.office.controller.OfficeController;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.service.OfficeService;


/**
 * Created on 15.03.2018.
 */
@RestController
@RequestMapping(value = "/api/office")
public class OfficeControllerImpl implements OfficeController {

    private OfficeService service;

    @Autowired
    public OfficeControllerImpl(OfficeService service) {
        this.service = service;
    }

    //не работает отправка - проблема в классе office, CustomSuccessResponse отправляется отлично
    // причина оказалась в @org.springframework.data.annotation.Transient, когда поменял на @JsonIgnore заработало
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomDataOut<Office>> getOfficeById(@PathVariable long id) {
        Office office = service.getOfficeById(id);
        CustomDataOut<Office> dataOut = new CustomDataOut<>(office);
        return new ResponseEntity<>(dataOut, HttpStatus.FOUND);
    }
}
