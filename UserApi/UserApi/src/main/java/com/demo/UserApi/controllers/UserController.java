package com.demo.UserApi.controllers;

import com.demo.UserApi.models.dto.UserDto;
import com.demo.UserApi.models.entity.User;
import com.demo.UserApi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Trae una lista con todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "La lista de usuarios se encuentra vacia"),
            @ApiResponse(responseCode = "200", description = "Lista encontrada"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

    @Operation(summary = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error")})

    @PostMapping("/create-user/{dni}") //crea users
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto, @PathVariable int dni) {
        try {
            User savedUser = userService.createUser(userDto, dni);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); //Http 201
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Busca un usuario por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No se encontro usuario con ese Id"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

    @Operation(summary = "Actualiza los datos de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "No se encontro usuario con ese Id"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

    @Operation(summary = "Elimina un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "No se encontro usuario con ese Id"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

    @Operation(summary = "Busca el id por su username", description = "Se le pasa por el path el username, busca en base de datos ese username y devuelve el id de ese usuario" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontro usuario con ese Username"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

