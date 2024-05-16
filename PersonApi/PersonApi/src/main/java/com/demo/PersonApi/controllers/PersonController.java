package com.demo.PersonApi.controllers;

import com.demo.PersonApi.models.entities.Person;
import com.demo.PersonApi.models.dtos.PersonDto;
import com.demo.PersonApi.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Trae una lista con todos las personas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "La lista de personas se encuentra vacia"),
            @ApiResponse(responseCode = "200", description = "Lista encontrada"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

    @Operation(summary = "Crea una nueva persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Persona creada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

    @Operation(summary = "Busca una persona por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontro una persona con ese Id"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

    @Operation(summary = "Actualiza los datos de una persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona actualizada"),
            @ApiResponse(responseCode = "404", description = "No se encontro una persona con ese Id"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

    @Operation(summary = "Elimina una persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona eliminada"),
            @ApiResponse(responseCode = "404", description = "No se encontro una persona con ese Id"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

    @Operation(summary = "Busca el id por su DNI", description = "Se le pasa por el path el DNI, busca en base de datos una persona con ese DNI y devuelve el id de la persona" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontro usuario con ese Username"),
            @ApiResponse(responseCode = "500", description = "Error")})

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
