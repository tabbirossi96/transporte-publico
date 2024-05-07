package com.demo.PersonApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class PersonApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonApiApplication.class, args);
	}

}
