package com.pills.pillstracker.services;

import com.pills.pillstracker.exceptions.PersonAlreadyExistException;
import com.pills.pillstracker.models.daos.Person;


public interface PersonService {

    Person registerNewPerson(Person person) throws PersonAlreadyExistException;

}
