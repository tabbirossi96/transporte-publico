package com.demo.EcobiciApi.controllers;

import com.demo.EcobiciApi.exceptions.LengthExceptions;
import com.demo.EcobiciApi.exceptions.OnlyLettersException;
import com.demo.EcobiciApi.models.dtos.stations.StationFavDto;
import com.demo.EcobiciApi.models.entities.StationAttribute;
import com.demo.EcobiciApi.models.entities.StationFavorite;
import com.demo.EcobiciApi.services.EcobiciService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;


@RestController
@RequestMapping("/stations")

public class EcobiciController {

    @Autowired
    EcobiciService ecobiciService;

    //devuelva todas las estaciones existentes
    @GetMapping("/all-station")
    public ResponseEntity<?> getStationAttributes(@RequestParam String clientId, @RequestParam String clientSecret) {
        try {
            List<StationAttribute> stationAttributes = ecobiciService.getStationAttributes(clientId, clientSecret);
            if (stationAttributes.isEmpty()) { //si la lista esta vacia...
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //Http 204
            } //si hay objetos en la lista
            return new ResponseEntity<>(stationAttributes, HttpStatus.OK);
        } //otro error...
            catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //guardar estacion favorita
    @PostMapping("/save-stationfav/{station_id}/{username}")
    //request:id_estacion_fav, nombre usuario
    public ResponseEntity<?> createStationFavorite(@RequestBody String alias, @PathVariable Long station_id, @PathVariable String username,
                                                   @RequestParam String clientId, @RequestParam String clientSecret) throws Exception {
        try {
            StationFavorite saveStationFav = ecobiciService.saveStationFavorite(alias, station_id, username, clientId, clientSecret);
            return new ResponseEntity<>(saveStationFav, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //levantar las estaciones favortias POR Usuario
    @GetMapping("/station-fav-by-user/{user_id}")
    public ResponseEntity<?> getStationFavByUserId(@PathVariable Long user_id) {
        try {
            List<StationFavDto> stationFavDtos = ecobiciService.getStationFavByUserId(user_id);
            return new ResponseEntity<>(stationFavDtos, HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
                      return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
                 return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/stationFav/{id}")
    public ResponseEntity<?> updateStationFav(@PathVariable Long id, @RequestBody String alias) {
        try {
            StationFavorite updatedStationFav = ecobiciService.updateStationFav(id, alias);
            return ResponseEntity.ok(updatedStationFav);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (LengthExceptions | OnlyLettersException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el alias: " + e.getMessage());
        }
    }
}
