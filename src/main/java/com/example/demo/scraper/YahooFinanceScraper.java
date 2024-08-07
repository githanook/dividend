package com.example.demo.scraper;

import com.example.demo.model.Company;
import com.example.demo.model.Dividend;
import com.example.demo.model.ScrapedResult;
import com.example.demo.model.constants.Month;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class YahooFinanceScraper implements Scraper {
    private static final String STATIC_URL = "https://finance.yahoo.com/quote/COKE/history/?period1=1565060909&period2=1722912527";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";
    private static final long START_TIME=86400; //1일 60초 * 60분 * 24시간
    @Override
    public ScrapedResult scrap(Company company){
        var scrapResult = new ScrapedResult();
        scrapResult.setCompany(company);
        try{

            long now = System.currentTimeMillis() / 1000;
            String url = String.format(STATIC_URL,company.getTicker(),START_TIME,now);
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            // Elements parsingDivs = document.getElementsByAttributeValue("data-test", "history-table");
            Elements parsingDivs = document.getElementsByClass("table yf-ewueuo");
            // System.out.println(parsingDivs);
            Element tableEle = parsingDivs.get(0);

            Element tbody = tableEle.children().get(1);
            List<Dividend> dividends = new ArrayList<>();
            for(Element e : tbody.children()){
                String txt = e.text();
                if(!txt.endsWith("Dividend")){
                    continue;
                }
                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.valueOf(splits[1].replace(",",""));
                int year = Integer.valueOf(splits[2]);
                String dividend = splits[3];

                if(month < 0){
                    throw new RuntimeException("Unexpected Month enum value ->" + splits[0]);
                }
                dividends.add(Dividend.builder()
                                    .date(LocalDateTime.of(year,month,day,0,0))
                                    .dividend(dividend)
                                    .build());
                //System.out.println(year + "/" + month + "/" + day + "->" + dividend);
                //System.out.println(txt);

            }
            scrapResult.setDividends(dividends);
            //   System.out.println(ele);
        }catch (IOException e){
            e.printStackTrace();
        }




        return scrapResult;
    }

    @Override
    public Company scrapCompanyByTicker(String ticker){
        String url = String.format(SUMMARY_URL, ticker, ticker);
        try{
            Document document = Jsoup.connect(url).get();
            System.out.println("document:::::::  "+document);
            Element titleEle = document.getElementsByTag("h1").get(1);


            //TICKER 문자열 개수
            int a = 0;
            int b=0;
            String[] words = ticker.split("");
            a=words.length;
            String[] words2 = titleEle.text().split("");
            b=words2.length;
            System.out.println("a:::::: "+a);
            System.out.println("b:::::: "+b);
            int c = b-a-2;
            System.out.println("c ::: "+c);


            System.out.println("title::::: "+titleEle);

            String title5 = titleEle.text().substring(0,c).trim();
            String title3 = titleEle.text().split("[.]")[0].trim();
            String title4 = titleEle.text();
//            String title = titleEle.text().split(" - ")[1].trim();
//            String title2 = titleEle.text().split(" - ")[0].trim();
            //String title = titleEle.text();
            System.out.println("@@@@@@   "+title3);
            System.out.println("%%%%%%   "+title5);
//            System.out.println("title::"+title);
//            System.out.println("title::"+title2);

            return Company.builder()
                        .ticker(ticker)
                        .name(title5)
                         .build();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }
}
