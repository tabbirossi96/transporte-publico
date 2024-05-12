package com.demo.UserApi.controllers;

import com.demo.UserApi.models.dto.PersonDto;
import com.demo.UserApi.models.entity.User;
import com.demo.UserApi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all-user") //trae todos los users
    public List<User> allUser(){
        return userService.allUser();
    }

    @PostMapping("/create-user") //crea users
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping("/find-user/{id}") // busca user por id
    public User findUser(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @PutMapping("/update-user") // actualiza users
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }

    @DeleteMapping("/delete-user/{id}") // elimina users
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
//----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/all-person") //trae todas las personas
    public List<PersonDto> allPerson(){
        return userService.allPersons();
    }

    @GetMapping("/find-person/{id}") //busca personas por id
    public Optional<PersonDto> findPerson(@PathVariable Long id){
        return userService.findPerson(id);
    }
//----------------------------------------------------------------------------------------------------------------------

}

