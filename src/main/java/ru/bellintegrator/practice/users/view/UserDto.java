package ru.bellintegrator.practice.users.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.bellintegrator.practice.users.model.User;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created on 18.03.2018.
 */
public class UserDto {

    private Long id;
    private String firstName;
    private String secondName;
    private String middleName;
    private String position;
    private String phone;
    private String docName;
    private String docNumber;
    private Date docDate;
    private String citizenshipName;
    private int citizenshipCode;
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
        docName = user.getDocument().getDocName();
        docNumber = user.getDocNumber();
        docDate = user.getDocDate();
        citizenshipName = user.getCountry().getName();
        citizenshipCode = user.getCountry().getCode();
        isIdentified = user.isIdentified();
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

    public int getCitizenshipCode() {
        return citizenshipCode;
    }

    public void setCitizenshipCode(int citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }

    @JsonProperty(value = "isIdentified")
    public Boolean getIdentified() {
        return isIdentified;
    }

    public void setIdentified(Boolean identified) {
        isIdentified = identified;
    }
}
