package ru.bellintegrator.practice.office.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.bellintegrator.practice.orgs.model.Organisation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Офис организации.
 */
@Entity
@Table(name = "offices")
public class Office implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

//    @org.springframework.data.annotation.Transient - из-за этого не работала сериализация
    @JsonIgnore
    @Version
    private Integer version = 0;

//    @org.springframework.data.annotation.Transient
    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "org_id")
    private Organisation organisation;

    @Transient
    private Long orgId; // для JSON маппинга

    @NotNull
    @Size(min = 3, max = 255)
    @Basic(optional = false)
    private String name;

    private String address;

    private String phone;

    @Column(name = "is_active")
    private Boolean isActive;

    public Office() {
    }

    // добавить конструктор с параметрами при тестировании

    public Long getId() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty(value = "isActive")
    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
