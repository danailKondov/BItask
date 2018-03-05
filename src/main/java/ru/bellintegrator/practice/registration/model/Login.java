package ru.bellintegrator.practice.registration.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Логин, пароль и имя пользователя.
 */
@Entity
@Table(name = "logins")
public class Login implements Serializable{

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
    @Column(length = 50)
    private String password;

    public Login() {
    }

    public Login(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
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
}
