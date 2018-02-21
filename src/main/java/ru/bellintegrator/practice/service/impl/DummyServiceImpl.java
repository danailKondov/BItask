package ru.bellintegrator.practice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.dao.PersonDAO;
import ru.bellintegrator.practice.model.Person;
import ru.bellintegrator.practice.service.DummyService;
import ru.bellintegrator.practice.view.PersonView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class DummyServiceImpl implements DummyService {
    private final Logger log = LoggerFactory.getLogger(DummyServiceImpl.class);

    private final PersonDAO dao;

    @Autowired
    public DummyServiceImpl(PersonDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public void add(PersonView view) {
        Person person = new Person(view.name, view.age);
        dao.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonView> persons() {
        List<Person> all = dao.all();

        Function<Person, PersonView> mapPerson = p -> {
            PersonView view = new PersonView();
            view.id = String.valueOf(p.getId());
            view.name = p.getName();
            view.age = p.getAge();

            log.info(view.toString());

            return view;
        };

        return all.stream()
                .map(mapPerson)
                .collect(Collectors.toList());
    }
}
