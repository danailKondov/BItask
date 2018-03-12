package ru.bellintegrator.practice.orgs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.exceptionhandler.exceptions.OrganisationException;
import ru.bellintegrator.practice.orgs.dao.OrganisationRepository;
import ru.bellintegrator.practice.orgs.model.Organisation;
import ru.bellintegrator.practice.orgs.service.OrganisationService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

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
        Organisation organisation = repository.findOne(id); // по условию, возвращаем только активные
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
        if(repository.exists(organisation.getId())) {
            repository.save(organisation);
        } else {
            throw new OrganisationException("Невозможно обновить - организации с подобным ID нет в базе: " + organisation.getId());
        }
    }

    @Override
    public List<Organisation> getOrganisationsByCriteria(String name, Long inn, Boolean active) {
        Specification<Organisation> spec = new Specification<Organisation>() {
            @Override
            public Predicate toPredicate(Root<Organisation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (name != null) {
                    criteriaBuilder.and(criteriaBuilder.like(root.get("name"), name)); // неточное совпадение
                }
                if (inn != null) {
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("inn"), inn));
                }
                if (active != null) {
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("isActive"), active));
                }
                if (name == null && inn == null && active == null) {
                    // вернуть все организации
                }
                // TODO: завершить метод, добавить организации в data.sql и потестить, в регистрации логин должен быть email, плюс хэш в отдельный сервисный класс
                return criteriaBuilder.conjunction();
            }
        };
        return repository.findAll(spec);
    }
}
