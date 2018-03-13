package ru.bellintegrator.practice.orgs.view;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Класс необходим, чтобы отображать параметры запроса в контроллере OrganisationControllerImpl
 * (в методе getAllByCriteria) из объекта JSON в Java-объект.
 *
 * Created on 12.03.2018.
 */
public class CriteriaView {
    private String name;
    private Long inn;
    @JsonDeserialize(as=Boolean.class)
    private Boolean isActive;

    public CriteriaView() {
    }

    public CriteriaView(String name, long inn, boolean isActive) {
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

    public Long getInn() {
        return inn;
    }

    public void setInn(long inn) {
        this.inn = inn;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
