package com.pills.pillstracker.services;

import com.pills.pillstracker.exceptions.PersonAlreadyExistException;
import com.pills.pillstracker.models.daos.Person;

import java.util.Set;


public interface PersonService {

    Person registerNewPerson(Person person) throws PersonAlreadyExistException;

    Set<Person> retrieveAllPersonsForUser();

}
