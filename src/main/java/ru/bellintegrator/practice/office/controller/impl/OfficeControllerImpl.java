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
import ru.bellintegrator.practice.office.view.OfficeDto;
import ru.bellintegrator.practice.office.view.OfficeView;

import javax.validation.Valid;
import java.util.List;


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

    @Override
    @PostMapping(value = "/update")
    public ResponseEntity updateOffice(@RequestBody @Valid Office office) {
        service.updateOffice(office);
        return new ResponseEntity<>(new CustomSuccessResponse(), HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/delete")
    public ResponseEntity deleteOffice(@RequestBody Office office) {
        service.deleteOffice(office);
        return new ResponseEntity<>(new CustomSuccessResponse(), HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/save")
    public ResponseEntity saveOffice(@RequestBody Office office) {
        service.saveOffice(office);
        return new ResponseEntity<>(new CustomSuccessResponse(), HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/list")
    public ResponseEntity getAllByCriteria(@RequestBody OfficeView view) {
        List<OfficeDto> dtoList = service.getAllOfficesByCriterias(view.getOrgId(), view.getName(), view.getPhone(), view.getActive());
        CustomDataOut dataOut = new CustomDataOut<>(dtoList);
        return new ResponseEntity<>(dataOut, HttpStatus.FOUND);
    }
}
