package com.demo.PersonApi.services;

import com.demo.PersonApi.models.entities.Person;
import com.demo.PersonApi.models.dtos.PersonDto;
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
    public Person findPerson(Long id) {
        Person person;
        Optional<Person> personDB = personRepository.findById(id);
        if(personDB.isPresent()){
            person = personDB.get();
        }
        else {
            // TODO: REEMPLAZAR CON UN THROW EXEPTION 409 (CATCHAR EN EL CONTROLLER EL 409)
            person = new Person();
        }
        return person;
    }

    //actualizar persona
    public void updatePerson(Person person) {
        personRepository.findById(person.getId());
        personRepository.save(person);

    }

    //eliminar persona
    public void deletePerson(Long id){
        personRepository.deleteById(id);
    }

}

