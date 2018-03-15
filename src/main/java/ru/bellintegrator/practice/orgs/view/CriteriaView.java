package ru.bellintegrator.practice.orgs.view;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс необходим, чтобы отображать параметры запроса в контроллере OrganisationControllerImpl
 * (в методе getAllByCriteria) из объекта JSON в Java-объект.
 *
 * Created on 12.03.2018.
 */
public class CriteriaView {
    private String name;
    private String inn;
    @JsonProperty(value = "isActive")
    private Boolean isActive;

    public CriteriaView() {
    }

    public CriteriaView(String name, String inn, Boolean isActive) {
        this.name = name;
        this.inn = inn;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
