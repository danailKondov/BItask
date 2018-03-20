package ru.bellintegrator.practice.users.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.practice.exceptionhandler.exceptions.UserException;
import ru.bellintegrator.practice.office.dao.OfficeRepository;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.referencebook.dao.CountryRepository;
import ru.bellintegrator.practice.referencebook.dao.DocumentRepository;
import ru.bellintegrator.practice.users.dao.UserRepository;
import ru.bellintegrator.practice.users.model.User;
import ru.bellintegrator.practice.users.view.UserDto;
import ru.bellintegrator.practice.users.view.UserSaveDto;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created on 19.03.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private OfficeRepository officeRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getUserByIdWhenSuccessfulTest() {
        User user = new User();
        user.setFirstName("Name");
        when(userRepository.findOne(1L)).thenReturn(user);

        UserDto result = userService.getUserById(1L);

        UserDto expected = new UserDto(user);
        assertThat(result, is(expected));
    }

    @Test(expected = UserException.class)
    public void getUserByIdWhenFailTest() {
        when(userRepository.findOne(1L)).thenReturn(null);
        userService.getUserById(1L);
    }

    @Test(expected = UserException.class)
    public void updateUserWhenFailTest() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Name");

        when(userRepository.findOne(1L)).thenReturn(null);

        userService.updateUser(userDto);
    }

    @Test
    public void deleteUserWhenSuccessfulTest() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Name");

        userService.deleteUser(userDto);

        verify(userRepository).delete(1L);
    }

    @Test(expected = UserException.class)
    public void deleteUserWhenFailTest() {
        UserDto userDto = new UserDto();
        userService.deleteUser(userDto);
    }

    @Test(expected = UserException.class)
    public void saveUserWhenFailTest() {
        UserSaveDto userDto = new UserSaveDto();
        userDto.setId(1L);
        userDto.setFirstName("Name");
        Office office = new Office();
        office.setId(1L);
        userDto.setOfficeId(1L);

        when(officeRepository.findOne(1L)).thenReturn(null);

        userService.saveUser(userDto);
    }
}