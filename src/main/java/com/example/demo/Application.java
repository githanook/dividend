package com.example.demo;

import com.example.demo.model.Company;
import com.example.demo.model.ScrapedResult;
import com.example.demo.scraper.YahooFinanceScraper;
import org.jsoup.Connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

     SpringApplication.run(Application.class, args);
     //   YahooFinanceScraper scraper = new YahooFinanceScraper();
       // var result = scraper.scrap(Company.builder().ticker("0").build());
   //     var result = scraper.scrapCompanyByTicker("MMM");
     //   System.out.println(result);

    }

}
