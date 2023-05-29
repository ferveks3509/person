package ru.job4j.person.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.person.model.Person;
import ru.job4j.person.repository.PersonRepository;

import static java.util.Collections.emptyList;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private PersonRepository users;


    @Override
    public UserDetails loadUserByUsername(String Login) throws UsernameNotFoundException {
        Person user = users.findPersonByLogin(Login);
        if (user == null) {
            throw new UsernameNotFoundException(Login);
        }
        return new User(user.getLogin(), user.getPassword(), emptyList());
    }
}
