package ru.bellintegrator.practice.referencebook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.referencebook.dao.CountryRepository;
import ru.bellintegrator.practice.referencebook.dao.DocumentRepository;
import ru.bellintegrator.practice.referencebook.model.Country;
import ru.bellintegrator.practice.referencebook.model.Document;
import ru.bellintegrator.practice.referencebook.service.ReferenceBookService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created on 21.03.2018.
 */
@Service
@Repository
@Transactional (readOnly = true)
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class ReferenceBookServiceImpl implements ReferenceBookService {

    private DocumentRepository documentRepository;
    private CountryRepository countryRepository;

    @Autowired
    public ReferenceBookServiceImpl(DocumentRepository documentRepository, CountryRepository countryRepository) {
        this.documentRepository = documentRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Document> getAllDocs() {
        List<Document> result = new ArrayList<>();
        Iterator<Document> iterator = documentRepository.findAll().iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    @Override
    public List<Country> getAllCountries() {
        List<Country> result = new ArrayList<>();
        Iterator<Country> iterator = countryRepository.findAll().iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }
}
