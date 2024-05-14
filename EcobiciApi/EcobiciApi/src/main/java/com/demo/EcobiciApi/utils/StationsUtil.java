package com.demo.EcobiciApi.utils;

import com.demo.EcobiciApi.models.dtos.stations.StationFavDto;
import com.demo.EcobiciApi.models.entities.StationFavorite;
import org.springframework.stereotype.Component;

@Component

public class StationsUtil {

    //desde StationFaV a StationDto

    public StationFavDto StationFavToDto (StationFavorite stationFavorite){
        return new StationFavDto(stationFavorite.getStation_fav_id(), stationFavorite.getUser_id(),
                stationFavorite.getStation_id(), stationFavorite.getName(), stationFavorite.getAddress(),
                stationFavorite.getNum_bikes_available(), stationFavorite.getNum_docks_available(),
                stationFavorite.getStatus());
    }

    public StationFavorite DtoToStationFav (StationFavDto StationFavDto){
        return new StationFavorite(StationFavDto.getStation_fav_id(), StationFavDto.getUser_id(),
                StationFavDto.getStation_id(), StationFavDto.getName(), StationFavDto.getAddress(),
                StationFavDto.getNum_bikes_available(), StationFavDto.getNum_docks_available(),
                StationFavDto.getStatus());
    }

    public StationFavorite createStationFav (StationFavDto StationFavDto){
        StationFavDto newStationFavDto = new StationFavDto();
        newStationFavDto.get

    }

    public void createUser(User user){
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
