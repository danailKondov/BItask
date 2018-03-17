package ru.bellintegrator.practice.office.view;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 15.03.2018.
 */
public class OfficeView {

    private Long orgId;
    private String name;
    private String address;
    private String phone;
    @JsonProperty(value = "isActive")
    private Boolean isActive;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
