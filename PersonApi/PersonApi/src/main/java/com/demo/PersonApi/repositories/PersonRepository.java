package com.demo.PersonApi.repositories;

import com.demo.PersonApi.models.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByDNI(int dni);

}
