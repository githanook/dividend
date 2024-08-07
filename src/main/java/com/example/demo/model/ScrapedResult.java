package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor // 모든 필드 초기화하는 생성사 코드 사용
public class ScrapedResult {

    private Company company;

    private List<Dividend> dividendEntities;

    public ScrapedResult() {this.dividendEntities = new ArrayList<>(); }
}
