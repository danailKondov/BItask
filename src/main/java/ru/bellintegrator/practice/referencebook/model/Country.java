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
    private Long code;

    @Version
    private Integer version = 0;

    @Basic(optional = false)
    @Column(name = "country_name")
    private String name;

    public Country() {
    }

    public Country(Long code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
