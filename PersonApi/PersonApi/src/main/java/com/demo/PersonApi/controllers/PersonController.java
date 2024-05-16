package com.demo.PersonApi.controllers;

import com.demo.PersonApi.models.entities.Person;
import com.demo.PersonApi.models.dtos.PersonDto;
import com.demo.PersonApi.services.PersonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/persons")

public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/all-persons") //trae todas las personas
    public ResponseEntity<?> allPersons(){
        try{
            List<PersonDto> personDto = personService.allPersons();
            if (personDto.isEmpty()) { //si la lista esta vacia...
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //Http 204
            }//si encuentra la lista...
            return ResponseEntity.ok(personDto); //Http 200
        }//otro error...
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //Http 500
        }
    }

    @PostMapping("/create-person") //crear persona
    public ResponseEntity<?> createPerson(@RequestBody PersonDto personDto) {
       try {
           Person savedPerson = personService.savePerson(personDto);
           //si lo mandado por el body tiene el formato correcto...
           return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson); //Http 201
       } //si no...
       catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());//Http 500
        }
    }

    @GetMapping("/find-person/{id}") // busca persona por id
    public ResponseEntity<?> findPerson(@PathVariable Long id) {
        try {
            PersonDto responsePerson = personService.findPerson(id);
            //si encuentra el usuario
            return ResponseEntity.ok(responsePerson); //Http 200
        } //si no...
        catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Http 404
        }//otro error...
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //Http 500
        }
    }

    @PutMapping("/update-person") // actualiza persona
    public ResponseEntity<?> updatePerson(@RequestBody PersonDto personDto) {
        try {
            Person updatedPerson = personService.updatePerson(personDto);
            //si lo mandado por el body tiene el formato correcto...
            return ResponseEntity.ok(updatedPerson); //Http 200
        } //si no...
        catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Http 404
        }//otro error...
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //Http 500
        }
    }

    @DeleteMapping("/delete-person/{id}") // elimina persona
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        try {
            personService.deletePerson(id);
            //si lo mandado por el body tiene el formato correcto...
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //Http 204
        } //si no...
        catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Http 404
        }//otro error...
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //Http 500
        }
    }

//----------------------------------------------------------------------------------------------------------------------

    @GetMapping("/find-id-by-DNI/{dni}")
    public ResponseEntity<?> findIdByDNI(@PathVariable int dni) {
        try {
            Long personId = personService.getPersonIdByDNI(dni);
            return ResponseEntity.ok(personId);
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Http 404
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//----------------------------------------------------------------------------------------------------------------------

}
