package com.demo.EcobiciApi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component

public class EcobiciUtil {

    @Value("${ecobici.information-url}")
    private String URL_STATION_INFO;

    @Value("${ecobici.status-url}")
    private String URL_STATION_STATUS;

}
