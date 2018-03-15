package ru.bellintegrator.practice.office.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.exceptionhandler.exceptions.OfficeException;
import ru.bellintegrator.practice.office.dao.OfficeRepository;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.service.OfficeService;

/**
 * Created on 15.03.2018.
 */
@Service
@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class OfficeServiceImpl implements OfficeService {

    private OfficeRepository repository;

    @Autowired
    public OfficeServiceImpl(OfficeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Office getOfficeById(long id) {
        Office office = repository.findOne(id);
        if (office != null) {
            return office;
        } else {
            throw new OfficeException("В базе нет офиса с подобным ID: " + id);
        }
    }
}
