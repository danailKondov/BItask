package ru.bellintegrator.practice.orgs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.exceptionhandler.exceptions.OrganisationException;
import ru.bellintegrator.practice.orgs.dao.OrganisationRepository;
import ru.bellintegrator.practice.orgs.model.Organisation;
import ru.bellintegrator.practice.orgs.service.OrganisationService;

/**
 * Created on 11.03.2018.
 */
@Service
@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class OrganisationServiceImpl implements OrganisationService {

    private OrganisationRepository repository;

    @Autowired
    public OrganisationServiceImpl(OrganisationRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Organisation getOrganisationById(long id) {
        Organisation organisation = repository.findOrganisationByIsActiveIsTrueAndId(id); // по условию, возвращаем только активные
        if (organisation != null) {
            return organisation;
        } else {
            throw new OrganisationException("Организиции с подобным ID нет в базе: " + id);
        }
    }

    @Override
    public void save(Organisation organisation) {
        Organisation orgWithSameName = repository.findOrganisationByName(organisation.getName());
        if (orgWithSameName == null) {
            repository.save(organisation);
        } else {
            throw new OrganisationException("Невозможно создание новой организации - организация с подобным именем уже есть в базе: " + organisation.getName());
        }

    }

    @Override
    public void update(Organisation organisation) {

        // проблема: save сохраняет дубли даже когда есть ID
        if(repository.exists(organisation.getId())) {
            repository.save(organisation);
        } else {
            throw new OrganisationException("Невозможно обновить - организации с подобным ID нет в базе: " + organisation.getId());
        }

//        костыль ниже так же не работает:
//        Organisation orgToUpdate = repository.findOne(organisation.getId());
//        if (orgToUpdate != null) {
//            if (organisation.getName() != null) orgToUpdate.setName(organisation.getName());
//            repository.save(orgToUpdate);
//        } else {
//            throw new OrganisationException("Невозможно обновить - организации с подобным ID нет в базе: " + organisation.getId());
//        }
    }
}
