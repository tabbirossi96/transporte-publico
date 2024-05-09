package com.demo.UserApi.services;

import com.demo.UserApi.models.dto.PersonDto;
import com.demo.UserApi.models.entity.User;
import com.demo.UserApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonClient personClient;

    //traer una lista con todos los usuarios
    public List<User> allUser(){
        return userRepository.findAll();
    }

    //crear usuarios
    public void createUser(Long id, User user){
        //Creo una nueva instancia llamada newUser. Esta instancia representa al usuario que deseo crear
        User newUser = new User();
        // Llama al PersonClient(que se conecta por feing con PersonApi) para obtener el PersonDto completo
        PersonDto person = personClient.findPerson(id);
        // Defino los valores de los atributos de mi nuevo User. esto ingresa por body
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setStatus(user.getStatus());
        // Asigno el Id del PersonDto al atributo personId de mi nuevo User
        newUser.setPersonId(person.getId());
        // Guardo el nuevo objeto de la clase User en mi user-bd
        userRepository.save(newUser);
    }

    //buscar usuarios por id
    public Optional<User> findUser(Long id){
        return userRepository.findById(id);
    }

    //actualizar usuarios
    public Optional<User> updateUser(User user) {
        userRepository.save(user);
        return userRepository.findById(user.getId());
    }

    //eliminar usuarios
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

//----------------------------------------------------------------------------------------------------------------------
    //este metodo devuelve una lista de objetos PersonDto
    public List<PersonDto> allPersons(){
        // invoca un metodo "allPersons" del personClient, trae una lista de persons del microservicio PersonApi
        return personClient.allPersons();
    }

    // metodo que busca una persona por id
    // un Optional que puede contener un objeto o estar vacío (si no encuentra el valor solicitado)
    // TODO: MEJORA SI EL OBJETO ESTA VACIO DEVOLVER UN MENSAJE QUE LO ACLARE (mejorar tambien en PersonApi)
    public Optional<PersonDto> findPerson(Long id){
        //invoca un metodo "findPerson" del personClient, busca una persona por ID del microservicio PersonApi
        // el "Optional.ofNullable(...)" envuelve el resultado en un Optional, porque asi el metodo lo solicita
        return Optional.ofNullable(personClient.findPerson(id));
    }
//----------------------------------------------------------------------------------------------------------------------




}
