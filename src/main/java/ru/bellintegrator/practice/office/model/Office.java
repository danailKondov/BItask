package ru.bellintegrator.practice.office.model;

import ru.bellintegrator.practice.orgs.model.Organisation;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Офис организации.
 */
@Entity
@Table(name = "offices")
public class Office implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Version
    private Integer version = 0;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "org_id")
    private Organisation organisation;

    @Basic(optional = false)
    private String name;

    private String address;

    private long phone;

    @Column(name = "is_active")
    private boolean isActive;

    public Office() {
    }

    public Office(Organisation organisation, String name, String address, long phone, boolean isActive) {
        this.organisation = organisation;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    // обязательный параметр orgId получаем через организацию: getOrganisation().getId()
    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
