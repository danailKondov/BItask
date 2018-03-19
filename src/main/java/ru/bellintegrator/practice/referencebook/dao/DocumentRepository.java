package ru.bellintegrator.practice.referencebook.dao;

import org.springframework.data.repository.CrudRepository;
import ru.bellintegrator.practice.referencebook.model.Document;

/**
 * Created on 18.03.2018.
 */
public interface DocumentRepository extends CrudRepository<Document, Long> {

    Document findDocumentByDocName(String name);
}
