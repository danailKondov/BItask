package ru.bellintegrator.practice.orgs.model;

import org.springframework.data.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Organisation.
 */
@Entity
@Table(name = "organisations")
public class Organisation implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @org.springframework.data.annotation.Transient
    @Version
    private Integer version = 0; // Оптимистическая блокировка не будет работать, если поле, аннотированное с помощью @Version, установлено на null

    @NotNull // для валидации - обязательный параметр
    @Size(min=3, max=100)
    @Basic(optional = false) // вероятно, из-за валидации эти ограничения избыточны?
    @Column(length = 100) // ?
    private String name;

    @Column(name = "full_name")
    private String fullName;

    @Size(min = 12, max = 12)
    private String inn; // переделал с long чтобы не возиться с BigInteger

    @Size(min = 9, max = 9)
    private String kpp;

    private String address;

    private String phone;

    @Column(name = "is_active")
    private boolean isActive;

    public Organisation() {
    }

    public Organisation(String name) {
        this.name = name;
    }

    public Organisation(String name, String fullName, String inn, String kpp, String address, String phone, boolean isActive) {
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organisation that = (Organisation) o;

        if (isActive() != that.isActive()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getFullName() != null ? !getFullName().equals(that.getFullName()) : that.getFullName() != null)
            return false;
        if (getInn() != null ? !getInn().equals(that.getInn()) : that.getInn() != null) return false;
        if (getKpp() != null ? !getKpp().equals(that.getKpp()) : that.getKpp() != null) return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        return getPhone() != null ? getPhone().equals(that.getPhone()) : that.getPhone() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getFullName() != null ? getFullName().hashCode() : 0);
        result = 31 * result + (getInn() != null ? getInn().hashCode() : 0);
        result = 31 * result + (getKpp() != null ? getKpp().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (isActive() ? 1 : 0);
        return result;
    }
}
