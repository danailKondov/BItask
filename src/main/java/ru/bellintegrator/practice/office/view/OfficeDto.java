package ru.bellintegrator.practice.office.view;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 16.03.2018.
 */
public class OfficeDto {

    private Long id;
    private String name;
    private Boolean isActive;

    public OfficeDto() {
    }

    public OfficeDto(Long id, String name, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value = "isActive")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
