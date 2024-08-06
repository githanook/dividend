package com.example.demo;

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

  //      SpringApplication.run(Application.class, args);

        try{
            Connection connection = Jsoup.connect("https://finance.yahoo.com/quote/COKE/history/?period1=1565060909&period2=1722912527");
            Document document = connection.get();

           // Elements eles = document.getElementsByAttributeValue("data-test", "history-table");
            Elements eles = document.getElementsByClass("table yf-ewueuo");
           // System.out.println(eles);
            Element ele = eles.get(0);

            Element tbody = ele.children().get(1);
            for(Element e : tbody.children()){
                String txt = e.text();
                if(!txt.endsWith("Dividend")){
                    continue;
                }
                String[] splits = txt.split(" ");
                String month = splits[0];
                int day = Integer.valueOf(splits[1].replace(",",""));
                int year = Integer.valueOf(splits[2]);
                String dividend = splits[3];
                System.out.println(year + "/" + month + "/" + day + "->" + dividend);
                //System.out.println(txt);
            }
         //   System.out.println(ele);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
