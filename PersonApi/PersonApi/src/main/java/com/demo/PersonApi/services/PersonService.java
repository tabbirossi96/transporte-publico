package com.demo.PersonApi.services;

import com.demo.PersonApi.models.Person;
import com.demo.PersonApi.models.PersonDto;
import com.demo.PersonApi.repositories.PersonRepository;
import com.demo.PersonApi.utils.PersonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class PersonService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonUtil personUtil;

    //treaer todas las personas
    public List<PersonDto> allPersons(){
        List<Person> personList = personRepository.findAll();
        return personUtil.personMapper(personList);
    }

    //crear persona
    public Person createPerson(Person person){
        return personRepository.save(person);
    }

    //buscar persona
    public Optional<Person> findPerson(Long id) {
        return personRepository.findById(id);
    }

    //actualizar persona
    public Optional<Person> updatePerson(Person person) {
        personRepository.save(person);
        return personRepository.findById(person.getId());
    }

    //eliminar persona
    public void deletePerson(Long id){
        personRepository.deleteById(id);
    }

}

