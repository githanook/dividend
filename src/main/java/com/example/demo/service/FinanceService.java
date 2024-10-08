package com.example.demo.service;

import com.example.demo.model.Company;
import com.example.demo.model.Dividend;
import com.example.demo.model.ScrapedResult;
import com.example.demo.model.constants.CacheKey;
import com.example.demo.persist.CompanyRepository;
import com.example.demo.persist.DividendRepository;
import com.example.demo.persist.entity.CompanyEntity;
import com.example.demo.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Cacheable(key = "#companyName", value = CacheKey.KEY_FINAL)
    public ScrapedResult getDividendByCompanyName(String companyName){
        log.info("search company ->" + companyName);

        //1. 회사명을 기준으로 회사 정보 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                                                             .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다."));

        System.out.println("company.getId = "+company.getId());
        //2. 조회된 회사 id로 배당금 조회
        List< DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());
        System.out.println("dividendEntities = "+dividendEntities);
        //3. 결과 조합 후 반환
        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> new Dividend(e.getDate(), e.getDividend()))
                .collect(Collectors.toList());

        return new ScrapedResult( new Company(company.getTicker(), company.getName()),
                dividends);
    }
}
