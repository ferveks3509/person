package ru.job4j.person.service;

import ru.job4j.person.model.Person;

import java.util.Collection;
import java.util.Optional;

public interface PersonService {
    Collection<Person> findAll();

    Person save(Person person);
    Optional<Person> findById(int id);
    void delete(Person person);
}
