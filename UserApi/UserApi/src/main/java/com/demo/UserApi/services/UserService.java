package com.demo.UserApi.services;

import com.demo.UserApi.exceptions.*;
import com.demo.UserApi.models.dto.PersonDto;
import com.demo.UserApi.models.dto.UserDto;
import com.demo.UserApi.models.entity.User;
import com.demo.UserApi.repositories.UserRepository;
import com.demo.UserApi.utils.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserUtil userUtil;
    @Autowired
    PersonClient personClient;

//----------------------------------------------------------------------------------------------------------------------
//  METODOS CRUD
//----------------------------------------------------------------------------------------------------------------------

    //traer una lista con todos los usuarios
    public List<UserDto> allUser() {
        //llamamos a la lista de la bd
        List<User> userList = userRepository.findAll();
        try { //si encuentra la lista
            return userUtil.userMapper((userList));
        } //otro error
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error", e);
        }
    }

    //crear usuarios
    public User createUser(UserDto userDto, int dni) throws Exception {
        //validacion de datos que ingresan por body
        this.validateAttributes(userDto);
        // Llama al PersonClient(que se conecta por feing con PersonApi) para obtener el person id con su dni
        Long personId = personClient.findIdByDNI(dni);
        //Creo una nueva instancia llamada newUser. Esta instancia representa al usuario que deseo crear
        UserDto newUser = new UserDto();
        // Defino los valores de los atributos de mi nuevo User. esto ingresa por body
        newUser.setUserName(userDto.getUserName());
        newUser.setPassword(userDto.getPassword());
        newUser.setPersonId(personId);
        //convierto el userDto a user
        User user = userUtil.dtoToUser(newUser);
        try {  // Guardo el nuevo objeto de la clase User en mi user-bd
            return userRepository.save(user);
        } //si no...
        catch (Exception e) {// excepción personalizada + HttpStatus 500
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al guardar el usuario en la base de datos", e);
        }
    }

    //buscar usuarios por id
    public UserDto findUser(Long id) {
        //declaramos una variable local (vacia)
        UserDto user;
        //el metodo del repositorio busca en la DB un user con el Id y lo guarda en el userDB, objeto tipo Optional
        Optional<User> userDB = userRepository.findById(id);
        //si el userDB trae un objeto tipo User, se asigna a userDto
        userDB.
                //si el userDB esta vacio (no encontro al user por id) lanza un exception(error)
                        orElseThrow(() ->
                        //exception especial de que un objeto no existe + mensaje personalizado
                        new EntityNotFoundException("No encontramos al usuario con el ID: " + id));
        // convierto de user a dto
        UserDto userDto = userUtil.userToDto(userDB.get());
        return userDto;
    }

    //actualizar usuarios
    public User updateUser(UserDto userDto) throws Exception {
        this.validateAttributes(userDto);
        Optional<User> userDB = userRepository.findById(userDto.getId());
        userDB.orElseThrow(() -> new EntityNotFoundException("No encontramos a la usuario con el ID: " + userDto.getId()));
        User user = userUtil.dtoToUser(userDto);
        try {
            // actualizar Person en la base de datos
            return userRepository.save(user);
            //si no...
        } catch (Exception e) {
            // excepción con mensaje + HttpStatus 500
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar los datos del usuario", e);
        }
    }

    //eliminar usuarios
    public void deleteUser(Long id) {
        Optional<User> userDB = userRepository.findById(id);
        userDB.orElseThrow(() -> new EntityNotFoundException("No encontramos al usuario con el ID: " + id));
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar al usuario", e);
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//  OTROS METODOS
//----------------------------------------------------------------------------------------------------------------------

    //metodo que busca un userId por username
    public Long getUserIdByUsername(String username) {
        //busco en la bd el usuario que tenga ese username
        User user = userRepository.findByUserName(username);
        //si encuentra el usuario, me devuelve el userId.
        if (user != null) {
            return user.getId();
        } else {
            throw new EntityNotFoundException("No encontramos el usuario");
        }
    }

    private void validateAttributes(UserDto userDto) throws Exception {
        String userName = userDto.getUserName();
        String password = userDto.getPassword();

        this.validateFormatUsername(userName);

        User existingUser = userRepository.findByUserName(userName);
        if (existingUser != null) {
            throw new UniqueException("Este nombre de usuario ya existe");
        }

        this.validateFormatPassword(password);
    }

    private void validateFormatUsername(String username) throws Exception {

        if (username == null || username.isEmpty()) {
            throw new NullOrVoidException("El usuario no puede estar vacio");
        }
        else if (username.length() < 5 || username.length() > 15) {
            throw new LengthExceptions("El usuario debe tener entre 5 y 15 caracteres");
        }
        else if (!username.matches("[a-zA-Z0-9]+")) {
            throw new NumbersAndLettersException("El usuario solo puede contener letras y números");
        }

    }

    private void validateFormatPassword(String password) throws Exception {

        if (password == null || password.isEmpty()) {
            throw new NullOrVoidException("La contraseña no puede estar vacía");
        }
        else if (password.length() < 8 || password.length() > 16) {
            throw new LengthExceptions("La contraseña debe tener de 8 a 16 caracteres");
        }
        else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            throw new FormatPasswordException("La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial");
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//  PRUEBA DE CONEXION
//----------------------------------------------------------------------------------------------------------------------

    public Long getPersonIdByDNI(int dni) {
        try {
            return personClient.findIdByDNI(dni);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el ID de la persona con DNI: " + dni, e);
        }
    }

//----------------------------------------------------------------------------------------------------------------------

}
