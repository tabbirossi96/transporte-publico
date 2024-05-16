package com.demo.EcobiciApi.services;

import com.demo.EcobiciApi.exceptions.LengthExceptions;
import com.demo.EcobiciApi.exceptions.OnlyLettersException;
import com.demo.EcobiciApi.models.dtos.stations.Root;
import com.demo.EcobiciApi.models.dtos.stations.Station;
import com.demo.EcobiciApi.models.dtos.stations.StationFavDto;
import com.demo.EcobiciApi.models.entities.StationAttribute;
import com.demo.EcobiciApi.models.entities.StationFavorite;
import com.demo.EcobiciApi.repositories.StationFavoriteRepository;
import com.demo.EcobiciApi.services.api.EcobiciClient;
import com.demo.EcobiciApi.models.dtos.stations.Data;
import com.demo.EcobiciApi.services.api.UserClient;
import com.demo.EcobiciApi.utils.StationsUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class EcobiciService {

    @Value("${ecobici.auth.clientId}")
    private String clientId;
    @Value("${ecobici.auth.clientSecret}")
    private String clientSecret;

    @Autowired
    StationFavoriteRepository stationFavoriteRepository;
    @Autowired
    EcobiciClient ecobiciClient;
    @Autowired
    UserClient userClient;
    @Autowired
    StationsUtil stationsUtil;

//----------------------------------------------------------------------------------------------------------------------
//  METODOS CRUD
//----------------------------------------------------------------------------------------------------------------------

    //create
    public StationFavorite saveStationFavorite(String alias, Long station_id, String username) throws Exception {
        this.validateFormatAlias(alias);
        //buscar el iduser en base al username
        Long userId = userClient.findIdByUsername(username);
        //busco en la lista de stationAttribute la estacion que quiero guardar en fav
        StationAttribute stationAttribute = this.findStationAttributeById(station_id);
        //traigo el objeto stationfavorite al que ya le guarde los atributos de la estacion indicada
        StationFavorite stationFavorite = getFavorite(alias, userId, stationAttribute);
        // Guardar el objeto StationFavorite en la base de datos
        try {
            return stationFavoriteRepository.save(stationFavorite);
        } catch (Exception e) {// excepción personalizada + HttpStatus 500
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al guardar", e);
        }
    }

    //get-all (read)
    public List<StationAttribute> getStationAttributes() {
        try {
            //crea una lista con las estaciones con los atributos de StationInformation
            List<StationAttribute> stationAttributes = this.getStationInformation();
            //a la lista anterior le agrega los atributos de stationStatus
            this.updateStationStatus(stationAttributes);
            //devuelvo la lista de estaciones con los atributos que yo quiero.
            return stationAttributes;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error", e);
        }
    }

    //find-by-userId List  (read)
    public List<StationFavDto> getStationFavByUserId(Long user_id) {
        //busca las stacionesfavoritas para ese user_id
        List<StationFavorite> stationDB = stationFavoriteRepository.findByUser_id(user_id);
        if (stationDB.isEmpty()) { //si la lista vuelve vacia
            throw new EntityNotFoundException("No encontramos estaciones favoritas");
        }
        List<StationFavDto> stationFavDtos = new ArrayList<>();
        for (StationFavorite stationFavorite : stationDB) { //recorro la lista
            //de stationFavorite a stationFavDto
            StationFavDto stationFavDto = stationsUtil.StationFavToDto(stationFavorite);
            //agrego la stationfavdto a la lista
            stationFavDtos.add(stationFavDto);
        }
        return stationFavDtos;

    }

    //update
    public StationFavorite updateStationFav(Long station_fav_id, String alias) throws Exception {
        try {
            //validar el formato del alias
            this.validateFormatAlias(alias);
            //busco en la bd la estacion favorita por su id
            StationFavorite stationFav = stationFavoriteRepository.findById(station_fav_id)
                    .orElseThrow(() -> //si no lo encuentro lanzo una exception
                            new EntityNotFoundException("No se puede encontrar la estacion"));
            //si la encuentro le cambio el alias
            stationFav.setAlias(alias);
            //guardo los cambios en la bd
            stationFavoriteRepository.save(stationFav);
            return stationFav;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar el alias: " + e.getMessage(), e);
        }
    }

    //delete
    public void deleteStationFav(Long station_fav_id) throws Exception {
        try {
            //busco en la bd la estacion favorita por su id
            Optional<StationFavorite> stationFav = stationFavoriteRepository.findById(station_fav_id);
            stationFav.orElseThrow(() -> //si no lo encuentro lanzo una exception
                    new EntityNotFoundException("No encontramos la estacion con el ID: " + station_fav_id));
            //si encuentro la estacion la elimino
            stationFavoriteRepository.deleteById(station_fav_id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la estacion favortia", e);
        }
    }


//----------------------------------------------------------------------------------------------------------------------
// OTROS METODOS
//----------------------------------------------------------------------------------------------------------------------

    private List<StationAttribute> getStationInformation() {
        //creo una lista vacía que luego tendra los atributos que yo quiero
        List<StationAttribute> stationAttributes = new ArrayList<>();
        // Llama al ecobiciClient, que esta se conecta con la API externa de Ecobici,
        // para obtener los datos de station information
        Root rootInfo = ecobiciClient.getStationInformation(clientId, clientSecret);
        Data dataInfo = rootInfo.getData();
        //obtengo la lista de stations information
        ArrayList<Station> stationsinfo = dataInfo.getStations();

        //itero dentro de la lista de station information
        for (Station station : stationsinfo) {
            //para cada estacion de la lista creo un nuevo objeto
            StationAttribute newStation = new StationAttribute();
            //a cada nuevo objeto, establesco los atributos que quiero de station information
            newStation.setStation_id(station.getStation_id());
            newStation.setName(station.getName());
            newStation.setAddress(station.getAddress());
            //cuando recolecte todos los atributos en el objeto newStation, los guardo en un StationAttribures
            stationAttributes.add(newStation);
        }
        return stationAttributes;
    }

    private void updateStationStatus(List<StationAttribute> stationAttributes) {
        //llamo a station status de la API externa
        Root rootStatus = ecobiciClient.getStationStatus(clientId, clientSecret);
        Data dataStatus = rootStatus.getData();
        ArrayList<Station> stationsStatus = dataStatus.getStations();

        //itero en la lista de station status
        //para obtener los atributos que quiero de esa lista

        //variable para usar de indice
        int i = 0;
        for (StationAttribute aux : stationAttributes) {
            for (Station stationStatus : stationsStatus) {
                //agarro el id de mi aux y busco en stationstatus el mismo id
                if (stationStatus.getStation_id().equals(aux.getStation_id())) {
                    aux.setNum_bikes_available(stationStatus.getNum_bikes_available());
                    aux.setNum_docks_available(stationStatus.getNum_docks_available());
                    aux.setStatus(stationStatus.getStatus());
                    stationAttributes.set(i, aux);
                    break;
                }
            }
            //me muevo una posicion en el indice por cada vuelta
            i++;
        }
    }

    private StationAttribute findStationAttributeById(Long station_id) {
        //traigo la lista completa de staciones con los atibutos
        List<StationAttribute> stationAttributes = getStationAttributes();
        for (StationAttribute sa : stationAttributes) {
            //busca la estacion que yo quiero poner como favorita comparando los station_id
            if (sa.getStation_id().equals(String.valueOf(station_id))) {
                return sa;
            }
        }
        throw new EntityNotFoundException("No se encontró la estación");
    }

    private StationFavorite getFavorite(String alias, Long userId, StationAttribute stationAttribute) {
        StationFavorite stationFavorite = new StationFavorite();
        //asignar los atributos que traigo desde stationAttribute
        stationFavorite.setUser_id(userId);
        stationFavorite.setStation_id(stationAttribute.getStation_id());
        stationFavorite.setName(stationAttribute.getName());
        stationFavorite.setAddress(stationAttribute.getAddress());
        stationFavorite.setNum_bikes_available(stationAttribute.getNum_bikes_available());
        stationFavorite.setNum_docks_available(stationAttribute.getNum_docks_available());
        stationFavorite.setStatus(stationAttribute.getStatus());
        stationFavorite.setAlias(alias);
        //los guardo en un objeto stationFavorite
        return stationFavorite;
    }

    // validacio alias
    private void validateFormatAlias(String alias) throws Exception {
        if (alias.length() < 3 || alias.length() > 15) {
            throw new LengthExceptions("El alias debe tener entre 3 y 15 caracteres");
        }
        if (!alias.matches("[a-zA-Z ]+")) {
            throw new OnlyLettersException("El alias solo puede contener letras");
        }
    }

//----------------------------------------------------------------------------------------------------------------------

}



