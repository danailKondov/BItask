package ru.bellintegrator.practice.registration.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Логин, пароль и имя пользователя.
 */
@Entity
@Table(name = "accounts")
public class Account implements Serializable{

    @Id
    @GeneratedValue
    private int id;

    @Version
    private Integer version;

    @Column(length = 50)
    private String name;

    @Basic(optional = false)
    @Column(length = 50)
    private String login;

    @Basic(optional = false)
    @Column(name = "password_SHA2_hash", length = 50)
    private String password;

    @Basic(optional = false)
    @Column(name = "is_activated")
    private boolean isActivated;

    @Basic(optional = false)
    @Column(name = "activation_code")
    private String activationCode;

    public Account() {
    }

    public Account(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
        isActivated = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
