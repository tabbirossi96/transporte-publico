package com.demo.PersonApi.services;

import com.demo.PersonApi.models.entities.Person;
import com.demo.PersonApi.models.dtos.PersonDto;
import com.demo.PersonApi.repositories.PersonRepository;
import com.demo.PersonApi.utils.PersonUtil;
import jakarta.persistence.EntityNotFoundException;
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
    public void createPerson(Person person){
        personRepository.save(person);
    }

    //buscar persona
    public Person findPerson(Long id) {
        //declaramos una variable local (vacia)
        Person person;
        //el metodo del repositorio busca en la BD un person con el Id y lo guarda en el personDB, objeto tipo Optional
        Optional<Person> personDB = personRepository.findById(id);
        //si el personDB trae un objeto tipo User, se asigna a person
        return personDB.
                //si el personDB esta vacio (no encontro al person por id) lanza un exception(error)
                        orElseThrow(() ->
                        //exception especial de que un objeto no existe + mensaje personalizado
                        new EntityNotFoundException("No encontramos a la persona con el ID: " + id));
    }

    //actualizar persona
    public void updatePerson(Person person) {
        Optional<Person> personDB = personRepository.findById(person.getId());
        personDB.orElseThrow(() -> new EntityNotFoundException("No encontramos a la persona con el ID: " + person.getId()));
        personRepository.save(person);
    }

    //eliminar persona
    public void deletePerson(Long id){
        Optional<Person> personDB = personRepository.findById(id);
        personDB.orElseThrow(() -> new EntityNotFoundException("No encontramos a la persona con el ID: " + id));
        personRepository.deleteById(id);
    }

}

