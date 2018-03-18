package ru.bellintegrator.practice.users.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.exceptionhandler.exceptions.UserException;
import ru.bellintegrator.practice.referencebook.model.Document;
import ru.bellintegrator.practice.users.dao.UserRepository;
import ru.bellintegrator.practice.users.model.User;
import ru.bellintegrator.practice.users.service.UserService;
import ru.bellintegrator.practice.users.view.UserDto;

/**
 * Created on 18.03.2018.
 */
@Service
@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = repository.findOne(id);
        if (user != null) {
            return new UserDto(user);
        } else {
            throw new UserException("В базе данных нет сотрудника с подобным ID: " + id);
        }
    }

    @Override
    public void updateUser(UserDto user) {
        Long id = user.getId();
        if (id == null) throw new UserException("Невозможно провести обновление пользователя - отсутсвует его ID.");
        User userFromDB = repository.findOne(id);
        if (userFromDB == null) throw new UserException("Невозможно провести обновление - пользователь с таким ID отсутсвует в базе данных.");
        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setMiddleName(user.getMiddleName());
        userFromDB.setLastName(user.getSecondName());
    }
}
