package com.demo.UserApi.controllers;

import com.demo.UserApi.models.dto.UserDto;
import com.demo.UserApi.models.entity.User;
import com.demo.UserApi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all-user") //trae todos los users
    public ResponseEntity<?> allUser() {
        try {
            List<UserDto> userDto = userService.allUser();
            if (userDto.isEmpty()) { //si la lista esta vacia...
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //Http 204
            }//si encuentra la lista...
            return ResponseEntity.ok(userDto); //Http 200
        }//otro error...
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //Http 500
        }
    }

    @PostMapping("/create-user/{dni}") //crea users
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto, @PathVariable int dni) {
        try {
            User savedUser = userService.createUser(userDto, dni);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); //Http 201
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/find-user/{id}") // busca user por id
    public ResponseEntity<?> findUser(@PathVariable Long id) {
        try {
            UserDto responseUser = userService.findUser(id);
            return new ResponseEntity<>(responseUser, HttpStatus.OK); //Http 202
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Http 404
        }//otro error...
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //Http 500
        }
    }

    @PutMapping("/update-user") // actualiza users
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        try {
            User updateUser = userService.updateUser(userDto);
            //si lo mandado por el body tiene el formato correcto...
            return ResponseEntity.ok(updateUser); //Http 200
        } //si no...
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Http 404
        }//otro error...
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //Http 500
        }
    }

    @DeleteMapping("/delete-user/{id}") // elimina users
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            //si lo mandado por el body tiene el formato correcto...
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //Http 204
        } //si no...
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Http 404
        }//otro error...
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //Http 500
        }
    }

//----------------------------------------------------------------------------------------------------------------------

    @GetMapping("/find-id-by-username/{username}") //busca el userid por su username
    public ResponseEntity<?> findIdByUsername(@PathVariable String username) {
        try {
            Long userId = userService.getUserIdByUsername(username);
            return ResponseEntity.ok(userId); //Http 200
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Http 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //Http 500
        }
    }

//----------------------------------------------------------------------------------------------------------------------

}

