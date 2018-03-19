package ru.bellintegrator.practice.users.view;

import ru.bellintegrator.practice.users.model.User;

/**
 * Created on 19.03.2018.
 */
public class UserListDto {

    private Long id;
    private String firstName;
    private String secondName;
    private String middleName;
    private String position;

    public UserListDto(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        secondName = user.getLastName();
        middleName = user.getMiddleName();
        position = user.getPosition();
    }

    public UserListDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
