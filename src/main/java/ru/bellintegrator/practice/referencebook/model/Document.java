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
    private int code;

    @Version
    private Integer version;

    @Basic(optional = false)
    @Column(name = "doc_name")
    private String docName;

    public Document() {
    }

    public Document(int code, String docName) {
        this.code = code;
        this.docName = docName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
