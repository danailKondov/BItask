package ru.bellintegrator.practice.referencebook.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс словаря по документам: код и название дока.
 */
@Entity
@Table(name = "documents")
public class Document implements Serializable {

    @Id
    private Long code;

    @Version
    private Integer version = 0;

    @Basic(optional = false)
    @Column(name = "doc_name")
    private String docName;

    public Document() {
    }

    public Document(Long code, String docName) {
        this.code = code;
        this.docName = docName;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
