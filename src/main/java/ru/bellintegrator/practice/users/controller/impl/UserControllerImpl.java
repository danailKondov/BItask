package ru.bellintegrator.practice.users.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.Utils.CustomDataOut;
import ru.bellintegrator.practice.Utils.CustomSuccessResponse;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.users.controller.UserController;
import ru.bellintegrator.practice.users.model.User;
import ru.bellintegrator.practice.users.service.UserService;
import ru.bellintegrator.practice.users.view.UserDto;

/**
 * Created on 18.03.2018.
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserControllerImpl implements UserController {

    private UserService service;

    @Autowired
    public UserControllerImpl(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        UserDto user = service.getUserById(id);
        CustomDataOut dataOut = new CustomDataOut<>(user);
        return new ResponseEntity<>(dataOut, HttpStatus.FOUND);
    }

    @PostMapping(value = "/update")
    public ResponseEntity updateUser (@RequestBody UserDto user) {
        service.updateUser(user);
        return new ResponseEntity<>(new CustomSuccessResponse(), HttpStatus.OK);
    }
}
