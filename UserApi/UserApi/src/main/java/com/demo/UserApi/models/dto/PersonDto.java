package com.demo.UserApi.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class PersonDto {
    private Long id;
    private String name;
    private String lastname;
    private int dni;
    private String email;
    private LocalDate birthdate;

}
