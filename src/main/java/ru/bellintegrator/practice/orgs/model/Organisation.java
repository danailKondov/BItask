package ru.bellintegrator.practice.orgs.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Organisation.
 */
@Entity
@Table(name = "organisations")
public class Organisation implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Version
    private Integer version;

    @Basic(optional = false)
    @Column(length = 100)
    private String name; // обязательный параметр

    @Column(name = "full_name")
    private String fullName;

    private long inn;

    private long kpp;

    private String address;

//    @OneToMany(mappedBy = "organisations")
//    private List<Office> offices = new ArrayList<>();

    private long phone;

    @Column(name = "is_active")
    private boolean isActive;

    public Organisation() {
    }

    public Organisation(String name) {
        this.name = name;
    }

    public Organisation(String name, String fullName, long inn, long kpp, String address, long phone, boolean isActive) {
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getInn() {
        return inn;
    }

    public void setInn(long inn) {
        this.inn = inn;
    }

    public long getKpp() {
        return kpp;
    }

    public void setKpp(long kpp) {
        this.kpp = kpp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
