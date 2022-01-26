package com.pills.pillstracker.services.impl;

import com.pills.pillstracker.exceptions.PersonAlreadyExistException;
import com.pills.pillstracker.models.daos.Person;
import com.pills.pillstracker.models.daos.User;
import com.pills.pillstracker.repositories.PersonRepository;
import com.pills.pillstracker.services.PersonService;
import com.pills.pillstracker.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Set;


@Slf4j
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final UserService userService;

    public PersonServiceImpl(PersonRepository personRepository, UserService userService) {

        this.personRepository = personRepository;
        this.userService = userService;
    }

    @Override
    public Person registerNewPerson(Person person) throws PersonAlreadyExistException {

        checkExistingFields(person);
        User parentUser = userService.getAuthenticatedUser();
        parentUser.addPerson(person);
        return personRepository.save(person);
    }

    @Override
    public Set<Person> retrieveAllPersonsForUser() {

        return userService.getAuthenticatedUser().getPersons();
    }

    private void checkExistingFields(Person person) throws PersonAlreadyExistException {

        if (personRepository.existsByContactNumber(person.getContactNumber())) {
            throw new PersonAlreadyExistException("register.err.contactNumberInUse");
        }
    }

}
