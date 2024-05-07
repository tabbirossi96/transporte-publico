package com.demo.UserApi.controllers;

import com.demo.UserApi.models.User;
import com.demo.UserApi.repositories.UserRepository;
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
    @Autowired
    UserRepository userRepository;

    @GetMapping("/all-user") //trae todas las personas
    public List<User> allUser(){
        return userService.allUser();
    }

    @PostMapping("/create-user") //crea persona
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping("/find-user/{id}") // busca persona
    public Optional<User> findUser(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @PutMapping("/update-user") // actualiza persona
    public Optional<User> updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete-user/{id}") // elimina persona
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

