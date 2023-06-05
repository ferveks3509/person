package ru.job4j.person.service;

import org.springframework.stereotype.Service;
import ru.job4j.person.model.Person;
import ru.job4j.person.repository.PersonRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
    }

    @Override
    public Collection<Person> findAll() {
        return (Collection<Person>) personRepository.findAll();
    }
}
