package com.demo.PersonApi.services;

import com.demo.PersonApi.exceptions.*;
import com.demo.PersonApi.models.entities.Person;
import com.demo.PersonApi.models.dtos.PersonDto;
import com.demo.PersonApi.repositories.PersonRepository;
import com.demo.PersonApi.utils.PersonUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service

public class PersonService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonUtil personUtil;

    //TODO: AGREGAR UNA EXCEPTION
    //treaer todas las personas
    public List<PersonDto> allPersons(){
        List<Person> personList = personRepository.findAll();
        return personUtil.personMapper(personList);
    }

    //crear persona
    public Person savePerson(PersonDto personDto) throws Exception {
        // Validar datos
        this.validateAttributes(personDto);

        // Convertir PersonDto a Person
        Person person = personUtil.dtoToPerson(personDto);
        try {
            // guardar Person en la base de datos
            return personRepository.save(person);
            //si no...
        } catch (Exception e) {
            // excepción personalizada + HttpStatus 500
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al guardar la persona en la base de datos", e);
        }
    }


    //buscar persona
    public PersonDto findPerson(Long id) {
        //declaramos una variable local (vacia)
        PersonDto person;
        //el metodo del repositorio busca en la BD un person con el Id y lo guarda en el personDB, objeto tipo Optional
        Optional<Person> personDB = personRepository.findById(id);
        //si el personDB trae un objeto tipo User, se asigna a personDto
        personDB.
                //si el personDB esta vacio (no encontro al person por id) lanza un exception(error)
                orElseThrow(() ->
                //exception especial de que un objeto no existe + mensaje personalizado
                new EntityNotFoundException("No encontramos a la persona con el ID: " + id));
        //convierto de person a dto
        PersonDto personDto = personUtil.personToDto(personDB.get());
        return personDto;

    }

    //actualizar persona
    public Person updatePerson(PersonDto personDto) throws Exception {
        this.validateAttributes(personDto);
        Optional<Person> personDB = personRepository.findById(personDto.getId());
        personDB.orElseThrow(() -> new EntityNotFoundException
                ("No encontramos a la persona con el ID: " + personDto.getId()));
        Person person = personUtil.dtoToPerson(personDto);
        try {
            // actualizar Person en la base de datos
            return personRepository.save(person);
            //si no...
        } catch (Exception e) {
            // excepción con mensaje + HttpStatus 500
            throw new ResponseStatusException( HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar los datos la persona", e);
        }
    }

    //eliminar persona
    public void deletePerson(Long id){
        Optional<Person> personDB = personRepository.findById(id);
        personDB.orElseThrow(() -> new EntityNotFoundException
                ("No encontramos a la persona con el ID: " + id));
        try {
            // eliminar Person en la base de datos
            personRepository.deleteById(id);
            //si no...
        } catch (Exception e) {
            // excepción con mensaje + HttpStatus 500
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar a la persona", e);
        }
    }

    //Validar el ingreso de los datos
    private void validateAttributes(PersonDto personDto) throws Exception {
        // Validar nombre
        if (personDto.getName() == null || personDto.getName().isEmpty()) {
            throw new NullOrVoidException("El nombre no puede ser nulo o vacío");
        }
        if (personDto.getName().length() < 3) { //valida que minimo haya 3 letras
            throw new CharactersMinExceptions("El nombre debe tener al menos 3 caracteres");
        }
        if (!personDto.getName().matches("[a-zA-Z]+")) { //valida que solo haya letras
            throw new OnlyLettersException("El nombre no puede contener números");
        }

        // Validar apellido
        if (personDto.getLastname() == null || personDto.getLastname().isEmpty()) {
            throw new NullOrVoidException("El apellido no puede ser nulo o vacío");
        }
        if (personDto.getLastname().length() < 3) { //valida que minimo haya 3 letras
            throw new CharactersMinExceptions("El apellido debe tener al menos 3 caracteres");
        }
        if (!personDto.getLastname().matches("[a-zA-Z]+")) { //valida que solo haya letras
            throw new OnlyLettersException("El apellido no puede contener números");
        }

        // Validar DNI
        if (personDto.getDni() >= 1 && personDto.getDni() < 99999999) {
            throw new NumberDniException("Numero de Dni incongruente");
        }

        // Validar email
        if (personDto.getEmail() == null || personDto.getEmail().isEmpty()) {
            throw new NullOrVoidException("El correo electrónico no puede ser nulo o vacío");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"; //valida el formato del email (zaraza123@example.com)
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(personDto.getEmail());
        if (!matcher.matches()) {
            throw new FormatEmailException("El correo electrónico tiene un formato incorrecto");
        }

        // Validar fecha de nacimiento
        if (personDto.getBirthdate() == null) {
            throw new NullOrVoidException("La fecha de nacimiento no puede ser nula");
        }
    }
}

