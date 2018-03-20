package ru.bellintegrator.practice.users.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.bellintegrator.practice.users.model.User;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created on 18.03.2018.
 */
public class UserDto {

//    private Long officeId;
    private Long id;
    private String firstName;
    private String secondName;
    private String middleName;
    private String position;
    private String phone;
    private String docName;
    private Long docCode;
    private String docNumber;
    private Date docDate;
    private String citizenshipName;
    private Long citizenshipCode;
    private Boolean isIdentified;

    public UserDto() {
    }

    public UserDto(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        secondName = user.getLastName();
        middleName = user.getMiddleName();
        position = user.getPosition();
        phone = user.getPhone();
        if (user.getDocument() != null) {
            docName = user.getDocument().getDocName();
            docCode = user.getDocument().getCode();
        } else {
            docName = null;
        }
        docNumber = user.getDocNumber();
        docDate = user.getDocDate();
        if (user.getCountry() != null) {
            citizenshipName = user.getCountry().getName();
            citizenshipCode = user.getCountry().getCode();
        } else {
            citizenshipName = null;
            citizenshipCode = null;
        }
        isIdentified = user.isIdentified();
    }

    public Long getDocCode() {
        return docCode;
    }

    public void setDocCode(Long docCode) {
        this.docCode = docCode;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getCitizenshipName() {
        return citizenshipName;
    }

    public void setCitizenshipName(String citizenshipName) {
        this.citizenshipName = citizenshipName;
    }

    public Long getCitizenshipCode() {
        return citizenshipCode;
    }

    public void setCitizenshipCode(Long citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }

    @JsonProperty(value = "isIdentified")
    public Boolean getIdentified() {
        return isIdentified;
    }

    public void setIdentified(Boolean identified) {
        isIdentified = identified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (getFirstName() != null ? !getFirstName().equals(userDto.getFirstName()) : userDto.getFirstName() != null)
            return false;
        if (getSecondName() != null ? !getSecondName().equals(userDto.getSecondName()) : userDto.getSecondName() != null)
            return false;
        if (getMiddleName() != null ? !getMiddleName().equals(userDto.getMiddleName()) : userDto.getMiddleName() != null)
            return false;
        if (getPosition() != null ? !getPosition().equals(userDto.getPosition()) : userDto.getPosition() != null)
            return false;
        if (getPhone() != null ? !getPhone().equals(userDto.getPhone()) : userDto.getPhone() != null) return false;
        return isIdentified != null ? isIdentified.equals(userDto.isIdentified) : userDto.isIdentified == null;
    }

    @Override
    public int hashCode() {
        int result = getFirstName() != null ? getFirstName().hashCode() : 0;
        result = 31 * result + (getSecondName() != null ? getSecondName().hashCode() : 0);
        result = 31 * result + (getMiddleName() != null ? getMiddleName().hashCode() : 0);
        result = 31 * result + (getPosition() != null ? getPosition().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (isIdentified != null ? isIdentified.hashCode() : 0);
        return result;
    }
}
