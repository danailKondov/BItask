package ru.bellintegrator.practice.office.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.bellintegrator.practice.orgs.model.Organisation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Офис организации.
 */
@Entity
@Table(name = "offices")
public class Office implements Serializable {

    @Id
    @GeneratedValue
    private long id;

//    @org.springframework.data.annotation.Transient - из-за этого не работала сериализация
    @JsonIgnore
    @Version
    private Integer version = 0;

//    @org.springframework.data.annotation.Transient
    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "org_id")
    private Organisation organisation;

    @NotNull
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

    public long getId() {
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

    @JsonProperty(value = "isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
