package ru.job4j.person.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findPersonByLogin(String login);
}
