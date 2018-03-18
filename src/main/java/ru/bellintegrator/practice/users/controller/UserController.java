package ru.bellintegrator.practice.users.controller;

import org.springframework.http.ResponseEntity;

/**
 * Created on 18.03.2018.
 */
public interface UserController {

    ResponseEntity getUserById(Long id);
}
