package ru.bellintegrator.practice.referencebook.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс словаря по странам: код страны и её название.
 */
@Entity
@Table(name = "countries")
public class Country implements Serializable{

    @Id
    private int code;

    @Version
    private Integer version = 0;

    @Basic(optional = false)
    @Column(name = "country_name")
    private String name;

    public Country() {
    }

    public Country(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
