package com.pills.pillstracker.repositories;

import com.pills.pillstracker.models.daos.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Boolean existsByContactNumber(String contactNumber);

}
