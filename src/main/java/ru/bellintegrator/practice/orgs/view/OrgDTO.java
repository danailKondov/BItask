package ru.bellintegrator.practice.orgs.view;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 15.03.2018.
 */
public class OrgDTO {

    private long id;
    private String name;
    private boolean isActive;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value = "isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
