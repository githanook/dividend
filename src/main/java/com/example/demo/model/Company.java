package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//GETTER, SETTER, LOMBOK, TOSTRING, EQUAL, REQUIREARGUMENTCONSTRUCTURE
@NoArgsConstructor
@AllArgsConstructor
//DTO, VO
public class Company {

    private String ticker;

    private String name;
}
