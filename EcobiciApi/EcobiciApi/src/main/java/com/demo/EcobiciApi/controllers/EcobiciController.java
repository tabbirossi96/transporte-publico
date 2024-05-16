package com.demo.EcobiciApi.controllers;

import com.demo.EcobiciApi.models.dtos.stations.StationFavDto;
import com.demo.EcobiciApi.models.entities.Alias;
import com.demo.EcobiciApi.models.entities.StationAttribute;
import com.demo.EcobiciApi.models.entities.StationFavorite;
import com.demo.EcobiciApi.services.EcobiciService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")

public class EcobiciController {

    @Autowired
    EcobiciService ecobiciService;

//----------------------------------------------------------------------------------------------------------------------

    //devuelva todas las estaciones existentes

    @Operation(summary = "Trae una lista de todas las estaciones de Ecobici", description = "Se conecta a la API Transporte, donde obtenemos información GTFS de las estaciones de Ecobici en CABA. Tomamos cada estacion, y los datos de ella que son de nuestro interes, y las guardamos en una lista de estaciones" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estaciones"),
            @ApiResponse(responseCode = "204", description = "Lista de estaciones vacia"),
            @ApiResponse(responseCode = "500", description = "Error")})

    @GetMapping("/all-station")
    public ResponseEntity<?> getStationAttributes() {
        try {
            List<StationAttribute> stationAttributes = ecobiciService.getStationAttributes();
            if (stationAttributes.isEmpty()) { //si la lista esta vacia...
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //Http 204
            } //si hay objetos en la lista
            return ResponseEntity.ok(stationAttributes); //Http 200
        } //otro error...
            catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Guardar estacion como favorito", description = "De la lista de estaciones, tomamos una por su station_id y le asignamos un usuario, y la guardamos en base de datos" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estacion favortia guardada"),
            @ApiResponse(responseCode = "500", description = "Error")})

    //guardar estacion favorita
    @PostMapping("/save-stationfav/{station_id}/{username}")
    public ResponseEntity<?> createStationFavorite(@RequestBody Alias alias, @PathVariable Long station_id,
                                                   @PathVariable String username) {
        try {
            StationFavorite saveStationFav = ecobiciService.saveStationFavorite(alias.getAlias(), station_id, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveStationFav); // Http 201
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // Http 500
        }
    }

    @Operation(summary = "Busca las estaciones favoritas de un usuario", description = "Busca en la base de datos todas las estaciones favortias que correspondan a un mismo usuario, y devuelve una lista con las mismas" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontro usuario con ese Username"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

    @Operation(summary = "Actualiza el alias de una estacion favortia" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontro usuario con ese Username"),
            @ApiResponse(responseCode = "500", description = "Error")})

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

    @Operation(summary = "Elimina una estacion como favortia de la base de datos" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontro usuario con ese Username"),
            @ApiResponse(responseCode = "500", description = "Error")})

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
