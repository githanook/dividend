package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data//GETTER, SETTER, LOMBOK, TOSTRING, EQUAL, REQUIREARGUMENTCONSTRUCTURE
@Builder //BUILDER 패턴
//DTO, VO
public class Company {

    private String ticker;

    private String name;
}
