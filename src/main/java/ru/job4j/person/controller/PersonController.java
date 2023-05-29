package ru.job4j.person.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.person.model.Person;
import ru.job4j.person.repository.PersonRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonRepository personRepository;

    @GetMapping("/")
    public List<Person> findAll() {
        return (List<Person>) this.personRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        if (id == 0 || id < 0) {
            throw new IllegalArgumentException("id cant zero or negative");
        }
        var person = personRepository.findById(id);
        if (person.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
        return new ResponseEntity<>(person.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        if (person.getLogin().length() < 3 || person.getPassword().length() < 3) {
            throw new IllegalArgumentException("Invalid password. Length must be more than 5 char");
        }
        if (person.getPassword() == null || person.getLogin() == null) {
            throw new NullPointerException("Login or password mustn't be empty");
        }
        return new ResponseEntity<Person>(
                this.personRepository.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        if (person.getPassword() == null || person.getLogin() == null) {
            throw new NullPointerException("Login or password mustn't be empty");
        }
        this.personRepository.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (id == 0 || id < 0) {
            throw new IllegalArgumentException("id cant zero or negative");
        }
        Person person = new Person();
        person.setId(id);
        this.personRepository.delete(person);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletResponse response) throws Exception {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        String err = e.getMessage() + e.getClass();
        response.getWriter().write(err);
    }
}