package com.demo.UserApi.services;

import com.demo.UserApi.models.dto.PersonDto;
import com.demo.UserApi.models.entity.User;
import com.demo.UserApi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
//todo: hacer lo del user dto y mapper
    //crear usuarios
    public void createUser(User user){
        //Creo una nueva instancia llamada newUser. Esta instancia representa al usuario que deseo crear
        User newUser = new User();
        // Llama al PersonClient(que se conecta por feing con PersonApi) para obtener el PersonDto completo
        PersonDto person = personClient.findPerson(user.getPersonId());
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
    public User findUser(Long id) {
        //declaramos una variable local (vacia)
        User user;
        //el metodo del repositorio busca en la BD un user con el Id y lo guarda en el userDB, objeto tipo Optional
        Optional<User> userDB = userRepository.findById(id);
        //si el userDB trae un objeto tipo User, se asigna a user
        return userDB.
                //si el userDB esta vacio (no encontro al user por id) lanza un exception(error)
                orElseThrow(() ->
                        //exception especial de que un objeto no existe + mensaje personalizado
                        new EntityNotFoundException("No encontramos al usuario con el ID: " + id));
    }
// ----------------------------------------------------------------------------------------------------
//    public User findUser(Long id) {
//        //declaramos una variable local (vacia)
//        User user;
//        //el metodo del repositorio busca en la BD un user con el Id y lo guarda en el userDB, objeto tip Optional
//        Optional<User> userDB = userRepository.findById(id);
//        //. Sino se le asigna un objeto vacio al user (argumento User::new)
//        user = userDB.orElseGet(User::new);
//        //el objeto obtenido se guarda en la variable user
//        return user;
//    }
// ----------------------------------------------------------------------------------------------------

    //actualizar usuarios
    public void updateUser(User user) {
        Optional<User> userDB = userRepository.findById(user.getId());
        userDB.orElseThrow(() -> new EntityNotFoundException("No encontramos a la usuario con el ID: " + user.getId()));
        userRepository.save(user);
    }

    //eliminar usuarios
    public void deleteUser(Long id){
        Optional<User> userDB = userRepository.findById(id);
        userDB.orElseThrow(() -> new EntityNotFoundException("No encontramos a la usuario con el ID: " + id));
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
