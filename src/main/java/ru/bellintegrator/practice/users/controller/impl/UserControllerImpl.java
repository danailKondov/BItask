package ru.bellintegrator.practice.users.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.users.view.UserListDto;
import ru.bellintegrator.practice.users.view.UserSaveDto;
import ru.bellintegrator.practice.utils.CustomDataOut;
import ru.bellintegrator.practice.utils.CustomSuccessResponse;
import ru.bellintegrator.practice.users.controller.UserController;
import ru.bellintegrator.practice.users.service.UserService;
import ru.bellintegrator.practice.users.view.UserDto;

import java.util.List;

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

    @PostMapping(value = "/delete")
    public ResponseEntity deleteUser(@RequestBody UserDto user) {
        service.deleteUser(user);
        return new ResponseEntity<>(new CustomSuccessResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity saveUser(@RequestBody UserSaveDto user) {
        service.saveUser(user);
        return new ResponseEntity<>(new CustomSuccessResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "/list")
    public ResponseEntity getAllUsersByCriteria(@RequestBody UserSaveDto user) {
        List<UserListDto> list = service.getAllUsersByCriterias(user);
        CustomDataOut dataOut = new CustomDataOut<>(list);
        return new ResponseEntity<>(dataOut, HttpStatus.FOUND);
    }
}
