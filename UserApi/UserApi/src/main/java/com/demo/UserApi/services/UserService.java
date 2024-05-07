package com.demo.UserApi.services;

import com.demo.UserApi.models.User;
import com.demo.UserApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    //traer todos los usuarios
    public List<User> allUser(){
        return userRepository.findAll();
    }

    //crear usuarios
    public User createUser(User user){
        return userRepository.save(user);
    }

    //buscar usuarios
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

}
