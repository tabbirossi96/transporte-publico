package com.demo.PersonApi.controllers;

import com.demo.PersonApi.models.Person;
import com.demo.PersonApi.models.PersonDto;
import com.demo.PersonApi.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/persons")

public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/all-persons") //trae todas las personas
    public List<PersonDto> allPersons(){
        return personService.allPersons();
    }

    @PostMapping("/create-person") //crea persona
    public void createPerson(@RequestBody Person person) {
        personService.createPerson(person);
    }

    @GetMapping("/find-person/{id}") // busca persona
    public Optional<Person> findPerson(@PathVariable Long id) {
        return personService.findPerson(id);
    }

    @PutMapping("/update-person") // actualiza persona
    public Optional<Person> updatePerson(@RequestBody Person person){
        return personService.updatePerson(person);
    }

    @DeleteMapping("/delete-person/{id}") // elimina persona
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }
}
