package com.rolands.ss_lv_scraping_service.service.impl;

import com.rolands.ss_lv_scraping_service.model.AdvertisementEntity;
import com.rolands.ss_lv_scraping_service.repositories.AdvertisementRepository;
import com.rolands.ss_lv_scraping_service.service.IScraperService;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
@NoArgsConstructor
public class ScraperServiceImpl implements IScraperService {
    static private Logger logger = LoggerFactory.getLogger(ScraperServiceImpl.class);
    List<AdvertisementEntity[]> allAdvertisements = new ArrayList<>();
    String[] areas = new String[3];

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Override
    public String fetchPage() {

        try {
            LinkedList<String> startEndpoints = new LinkedList<>(Arrays.asList(
                    "/lv/real-estate/flats"
            ));
            LinkedList<String> endpointTitles = new LinkedList<>(Arrays.asList("flats"));


            for (int i = 0; i < Math.min(startEndpoints.size(), endpointTitles.size()); i++) {
                areas = new String[3];
                areas[0] = endpointTitles.get(i);
                logger.info("Nomainijis tipu uz:" + areas[0]);
                goDeeper(startEndpoints.get(i));
            }

            return null;
        } catch (Exception e) {
            logger.info("Failed to fetchPage: " + e.getMessage());
            return "Failed to scrape: " + e.getMessage();
        }
    }

    private Object goDeeper(String url) {

        Map<String, Object> map = new HashMap<>();
        url = "https://www.ss.lv" + url;

        try {
            Document doc = Jsoup.connect(url).get();

            if(doc.select(".filter_second_line_dv").size() > 0) {
                int count = readPages(url, 1, 1);

                return allAdvertisements;
            } else {
                Elements links = doc.select("tbody h4 a");

                for(Element link : links) {
                    String href = link.attr("href");
                    String title = link.text();

                    if( title.equals("Visi sludinājumi")) {
                        logger.info("STOOOPPPP TIKAI NE : Visi sludinājumi");
                        continue;
                    }

                    if (areas[1] == null){
                        logger.info("Nomainijis pilsetu uz:" + title);
                        areas[1] = title;
                    } else if (areas[2] == null){
                        logger.info("Nomainijis rajonu uz:" + title);
                        areas[2] = title;
                    }

                    map.put(title, goDeeper(href));

                    if(areas[2] != null) {
                        areas[2] = null;
                    } else if(areas[1] != null) {
                        areas[1] = null;
                    }
                }
            }

        } catch (Exception e) {
            logger.info("Failed to goDeeper: " + e.getMessage());
            return new HashMap<>();
        }

        return map;
    }

    private int readPages(String url, int lives, int nextPage) throws IOException {

        String urlForPage = url + "/page" + nextPage +".html";
        Document doc = Jsoup.connect(urlForPage).get();
        Elements pageHref = doc.select("head link");
        int pageCounter = 0;

        boolean isFirstPage = pageHref.get(0).attr("href").contains("page1.html");
        if (isFirstPage && lives == 0){
            return 0;
        } else if (isFirstPage && lives >= 1){
            allAdvertisements.add(retrieveData(urlForPage));
            nextPage++;
            pageCounter = readPages(url, 0, nextPage) + 1;
        } else if(!isFirstPage && lives == 0){
            allAdvertisements.add(retrieveData(urlForPage));
            nextPage++;
            pageCounter = readPages(url, 0, nextPage) + 1;
        }

        return pageCounter;
    }

    private AdvertisementEntity[] retrieveData(String url) {
        AdvertisementEntity[] advertisements = null;

        try {
            // Fetch the HTML content from the website
            Document doc = Jsoup.connect(url).get();

            // Extract something, like all the links
            Elements rows = doc.select(".msga2-o");

            int loopCount = (rows.size() / 7);
            advertisements = new AdvertisementEntity[loopCount];

            for (int i = 0; i < rows.size(); i += 7) {
                int index = i / 7;
                advertisements[index] = new AdvertisementEntity();
                advertisements[index].setStreet(rows.get(i).text());
                advertisements[index].setRoomCount(rows.get(i+1).text());
                advertisements[index].setArea(extractNumber(rows.get(i+2).text()));
                advertisements[index].setFloor(rows.get(i+3).text());
                advertisements[index].setSeries(rows.get(i+4).text());
                advertisements[index].setPrice(extractNumber(rows.get(i+6).text()));
                advertisements[index].setType(areas[0]);
                advertisements[index].setCity(areas[1]);
                advertisements[index].setDistrict(areas[2]);

                advertisementRepository.save(advertisements[index]);
            }

            return advertisements;
        } catch (Exception e) {
            return advertisements;
        }
    }

    // Function to return the modified string
    static String extractNumber(String s) {

        // Replacing every non-digit character with a space (" ")
        s = s.replaceAll("[^\\d]", "");

        // Remove extra spaces from the beginning and the end of the string
        s = s.trim();

        // Replace all consecutive white spaces with a single space
        s = s.replaceAll(" +", "");

        // Return -1 if the string is empty
        if (s.equals("")) {
            return null;
        }

        return s;
    }
}