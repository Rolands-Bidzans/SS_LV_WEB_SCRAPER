package com.rolands.ss_lv_scraping_service.service.impl;

import com.rolands.ss_lv_scraping_service.service.IScraperService;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


@Service
@AllArgsConstructor
public class ScraperServiceImpl implements IScraperService {
    static private Logger logger = LoggerFactory.getLogger(ScraperServiceImpl.class);

    private Map<String, Object> goDeeper(String url) {

        Map<String, Object> map = new HashMap<>();
        url = "https://www.ss.lv" + url;

        try {
            Document doc = Jsoup.connect(url).get();

            if(doc.select(".filter_second_line_dv").size() > 0) {
                    retrieveData(url);
            } else {
                Elements links = doc.select("tbody h4 a");

                for(Element link : links) {
                    String href = link.attr("href");
                    String title = link.text();
                    logger.info(title);
                    logger.info("###########################");
                    map.put(title, goDeeper(href));
                }

            }

        } catch (Exception e) {
            return new HashMap<>();
        }

        return map;
    }


    @Override
    public String fetchPage() {
        LinkedList<String> startEndpoints = new LinkedList<>(Arrays.asList("/lv/real-estate/flats/"));
        LinkedList<String> endpointTitles = new LinkedList<>(Arrays.asList("flats"));

        Map<String,Object> map = new HashMap<>();

        try {

            for (int i = 0; i < Math.min(startEndpoints.size(), endpointTitles.size()); i++) {
                map.put(endpointTitles.get(i),goDeeper(startEndpoints.get(i)));
            }

            return map.toString();
        } catch (Exception e) {
            return "Failed to scrape: " + e.getMessage();
        }

    }

    private Map<String, String[]> retrieveData(String url) {
        Map<String,String[]> map = new HashMap<>();

        try {
            // Fetch the HTML content from the website
            Document doc = Jsoup.connect(url).get();

            // Extract something, like all the links
            Elements advertisements = doc.select(".msga2-o");

            // Return a simple summary

            String[] data = new String[6];
            String title="";
            int count = 0;
            for(Element info : advertisements){
                if(count>6){
                    map.put(title, data);
                    data = new String[6];
                    count = 0;
                }

                if(count == 0) {
                    title = info.text();
                    count++;
                    continue;
                }

                int index = count - 1;
                data[index] = info.text();
                count++;
            }

            map.forEach((key, value) -> {
                System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
            });

            logger.info("Retrieved data: {}", map);

            return map;

        } catch (Exception e) {
            return map;
        }
    }
}
