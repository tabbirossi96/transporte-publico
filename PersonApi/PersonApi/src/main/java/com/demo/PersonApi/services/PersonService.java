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

import java.time.LocalDate;
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

//----------------------------------------------------------------------------------------------------------------------
//  METODOS CRUD
//----------------------------------------------------------------------------------------------------------------------

    //treaer todas las personas
    public List<PersonDto> allPersons() {
        //llamamos a la lista de la bd
        List<Person> personList = personRepository.findAll();
        try { //si encuentra la lista
            return personUtil.personMapper(personList);
        } //otro error
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error", e);
        }
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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar los datos la persona", e);
        }
    }

    //eliminar persona
    public void deletePerson(Long id) {
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

//----------------------------------------------------------------------------------------------------------------------
//  OTROS METODOS
//----------------------------------------------------------------------------------------------------------------------

    //Metodo para validar el ingreso de los datos
    private void validateAttributes(PersonDto personDto) throws Exception {
        String name = personDto.getName();
        String lastname = personDto.getLastname();
        int dni = personDto.getDni();
        String email = personDto.getEmail();
        LocalDate birthdate = personDto.getBirthdate();

        this.validateFormatName(name);

        this.validateFormatLastname(lastname);

        this.validateFormatDNI(dni);

        Person existingPerson = personRepository.findByDni(personDto.getDni());
        if (existingPerson != null) {
            throw new UniqueException("Este DNI ya fue utilizado");
        }

        this.validateFormatEmail(email);

        this.validateFormatBirthdate(birthdate);
    }

    private void validateFormatName(String name) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new NullOrVoidException("Escribir nombre");
        }
        if (name.length() < 3) { //valida que minimo haya 3 letras
            throw new CharactersMinExceptions("El nombre debe tener al menos 3 caracteres");
        }
        if (!name.matches("[a-zA-Z]+")) { //valida que solo haya letras
            throw new OnlyLettersException("El nombre no puede contener números");
        }
    }

    private void validateFormatLastname(String lastname) throws Exception {
        if (lastname == null || lastname.isEmpty()) {
            throw new NullOrVoidException("Escribir apellido");
        }
        if (lastname.length() < 3) { //valida que minimo haya 3 letras
            throw new CharactersMinExceptions("El apellido debe tener al menos 3 caracteres");
        }
        if (!lastname.matches("[a-zA-Z]+")) { //valida que solo haya letras
            throw new OnlyLettersException("El apellido no puede contener números");
        }
    }

    private void validateFormatEmail(String email) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new NullOrVoidException("Escribir email");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"; //valida el formato del email (zaraza123@example.com)
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new FormatEmailException("El correo electrónico tiene un formato incorrecto");
        }
    }

    private void validateFormatDNI(int dni) throws Exception {
        if (dni < 1 && dni > 99999999) {
            throw new NumberDniException("Numero de Dni incongruente");
        }
    }

    private void validateFormatBirthdate(LocalDate birthdate) throws Exception {
        if (birthdate == null) {
            throw new NullOrVoidException("Colocar fecha de nacimiento");
        }
    }

    //metodo que busca un personId por DNI
    public Long getPersonIdByDNI(int dni) {
        //busco en la bd la persona que tenga ese DNI
        Person person = personRepository.findByDni(dni);
        //si encuentra la persona, me devuelve el personId.
        if (person != null) {
            return person.getId();
        } else {
            throw new EntityNotFoundException("No encontramos la persona");
        }
    }

//----------------------------------------------------------------------------------------------------------------------


}

