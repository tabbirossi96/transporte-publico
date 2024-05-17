package com.demo.EcobiciApi.utils;

import com.demo.EcobiciApi.models.dtos.stations.StationFavDto;
import com.demo.EcobiciApi.models.entities.StationFavorite;
import org.springframework.stereotype.Component;

@Component

public class StationsUtil {

    //desde StationFaV a StationDto

    public StationFavDto StationFavToDto (StationFavorite stationFavorite){
        return new StationFavDto(stationFavorite.getStation_fav_id(), stationFavorite.getUser_id(),
                stationFavorite.getStation_id(), stationFavorite.getName(), stationFavorite.getAddress(), stationFavorite.getAlias());
    }

    public StationFavorite DtoToStationFav (StationFavDto StationFavDto){
        return new StationFavorite(StationFavDto.getStation_fav_id(), StationFavDto.getUser_id(),
                StationFavDto.getStation_id(), StationFavDto.getName(), StationFavDto.getAddress(), StationFavDto.getAlias());
    }

}
