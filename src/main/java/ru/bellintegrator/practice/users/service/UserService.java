package ru.bellintegrator.practice.users.service;

import org.springframework.stereotype.Service;
import ru.bellintegrator.practice.users.model.User;
import ru.bellintegrator.practice.users.view.UserDto;
import ru.bellintegrator.practice.users.view.UserListDto;

import java.util.List;

/**
 * Created on 18.03.2018.
 */
public interface UserService {
    UserDto getUserById(Long id);
    void updateUser(UserDto user);
    void deleteUser(UserDto user);
    void saveUser(UserDto user);
    List<UserListDto> getAllUsersByCriterias(UserDto user);
}
