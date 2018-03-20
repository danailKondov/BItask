package ru.bellintegrator.practice.users.view;

/**
 * Created on 20.03.2018.
 */
public class UserSaveDto extends UserDto {

    private Long officeId;

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }
}
