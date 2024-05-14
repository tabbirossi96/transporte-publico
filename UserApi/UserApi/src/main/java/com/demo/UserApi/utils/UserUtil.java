package com.demo.UserApi.utils;

import com.demo.UserApi.models.dto.PersonDto;
import com.demo.UserApi.models.entity.User;
import org.springframework.stereotype.Component;

@Component

public class UserUtil {



    public User createUser(UserDto userDto){
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




}
