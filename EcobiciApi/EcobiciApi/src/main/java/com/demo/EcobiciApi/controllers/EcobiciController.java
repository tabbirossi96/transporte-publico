package com.demo.EcobiciApi.controllers;

import com.demo.EcobiciApi.exceptions.LengthExceptions;
import com.demo.EcobiciApi.exceptions.OnlyLettersException;
import com.demo.EcobiciApi.models.dtos.stations.StationFavDto;
import com.demo.EcobiciApi.models.entities.Alias;
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

//----------------------------------------------------------------------------------------------------------------------

    //devuelva todas las estaciones existentes
    @GetMapping("/all-station")
    public ResponseEntity<?> getStationAttributes(@RequestParam String clientId, @RequestParam String clientSecret) {
        try {
            List<StationAttribute> stationAttributes = ecobiciService.getStationAttributes(clientId, clientSecret);
            if (stationAttributes.isEmpty()) { //si la lista esta vacia...
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //Http 204
            } //si hay objetos en la lista
            return ResponseEntity.ok(stationAttributes); //Http 200
        } //otro error...
            catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //guardar estacion favorita
    @PostMapping("/save-stationfav/{station_id}/{username}")
    public ResponseEntity<?> createStationFavorite(@RequestBody Alias alias, @PathVariable Long station_id,
                                                   @PathVariable String username, @RequestParam String clientId,
                                                   @RequestParam String clientSecret) {
        try {
            StationFavorite saveStationFav = ecobiciService.saveStationFavorite(alias.getAlias(), station_id, username, clientId, clientSecret);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveStationFav); // Http 201
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // Http 500
        }
    }

    //busca las estaciones favortias por Usuario
    @GetMapping("/find-station-fav-by-user/{user_id}")
    public ResponseEntity<?> getStationFavByUserId(@PathVariable Long user_id) {
        try {
            List<StationFavDto> stationFavDtos = ecobiciService.getStationFavByUserId(user_id);
            return ResponseEntity.ok(stationFavDtos);
        }
        catch (EntityNotFoundException e) {
                      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()) ;
        }
    }

    //actualizar alias de la estacion favorita
    @PutMapping("/update-stationFav/{station_fav_id}")
    public ResponseEntity<?> updateStationFav(@PathVariable Long station_fav_id, @RequestBody Alias alias) {
        try {
            StationFavorite updatedStationFav = ecobiciService.updateStationFav(station_fav_id, alias.getAlias());
            return ResponseEntity.ok(updatedStationFav); // Http 200
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); //Http 404
//        } catch (LengthExceptions | OnlyLettersException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // Http 408
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // Http 500
        }
    }

    //elimina una estacion de las estaciones favortias de un usuario
    @DeleteMapping("/delete-stationfav/{station_fav_id}")
    public ResponseEntity<?> deleteStationFav(@PathVariable Long station_fav_id) {
        try {
            ecobiciService.deleteStationFav(station_fav_id);
            //si lo mandado por el body tiene el formato correcto...
            return ResponseEntity.accepted().body("Estacion eliminada de sus favoritos"); //Http 202
        } //si no...
        catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Http 404
        }//otro error...
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //Http 500
        }
    }

//----------------------------------------------------------------------------------------------------------------------

}
