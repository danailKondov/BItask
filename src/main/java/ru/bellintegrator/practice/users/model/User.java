package ru.bellintegrator.practice.users.model;

import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.utils.model.Country;
import ru.bellintegrator.practice.utils.model.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * Сотрудники организации, работающие в конкретном офисе.
 */
@Entity
@Table(name = "users")
public class User implements Serializable{

    @Id
    @GeneratedValue
    private int id;

    @Version
    private Integer version;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "office_id")
    private Office office;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(length = 50)
    private String position;

    private long phone;

    // приходят с фронта, где берутся из справочника
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "doc_code")
    private Document document;

    @Basic(optional = false)
    @Column(name = "doc_number")
    private long docNumber;

    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "doc_date")
    private Date docDate;

    // приходят с фронта, где берутся из справочника
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "citizenship_code")
    private Country country;

    @Column(name = "is_identified")
    private boolean isIdentified; // true, если подтверждены документы, удостоверяющие личность

    public User() {
    }

    public User(Office office, String firstName, String lastName, String middleName, String position, long phone, Document document, long docNumber, Date docDate, Country country, boolean isIdentified) {
        this.office = office;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.position = position;
        this.phone = phone;
        this.document = document;
        this.docNumber = docNumber;
        this.docDate = docDate;
        this.country = country;
        this.isIdentified = isIdentified;
    }

    public int getId() {
        return id;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public long getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(long docNumber) {
        this.docNumber = docNumber;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isIdentified() {
        return isIdentified;
    }

    public void setIdentified(boolean identified) {
        isIdentified = identified;
    }
}
