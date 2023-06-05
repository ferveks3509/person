package ru.job4j.person.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.person.model.Person;
import ru.job4j.person.service.PersonService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;


    @GetMapping("/")
    public List<Person> findAll() {
        return (List<Person>) this.personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        if (id == 0 || id < 0) {
            throw new IllegalArgumentException("id cant zero or negative");
        }
        var person = personService.findById(id);
        if (person.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
        return new ResponseEntity<>(person.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        if (person.getLogin().length() < 3 || person.getPassword().length() < 3) {
            throw new IllegalArgumentException("Invalid password. Length must be more than 5 char");
        }
        return new ResponseEntity<Person>(
                this.personService.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        if (person == null) {
            throw new NullPointerException("Login or password mustn't be empty");
        }
        this.personService.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (id == 0 || id < 0) {
            throw new IllegalArgumentException("id cant zero or negative");
        }
        Person person = new Person();
        person.setId(id);
        this.personService.delete(person);
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