package ru.bellintegrator.practice.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Человек
 */
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Long id;

    /**
     * Служебное поле hibernate
     */
    @Version
    private Integer version;

    /**
     * Иия
     */
    @Basic(optional = false)
    @Column(name = "first_name")
    private String name;

    /**
     * Возраст
     * т.к. поле примитивного типа, оно не может быть nullable
     */
    @Basic(optional = false)
    @Column(name = "age")
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    /**
     * Конструктор для hibernate
     */
    public Person() {

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{id:");
        builder.append(getId());
        builder.append(";name:");
        builder.append(getName());
        builder.append(";age:");
        builder.append(getAge());
        if (house != null) {
            builder.append(";address:");
            builder.append(house.getAddress());
        }
        builder.append("}");

        return builder.toString();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
