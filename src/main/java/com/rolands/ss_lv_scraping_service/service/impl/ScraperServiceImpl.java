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

    @Autowired
    AdvertisementRepository advertisementRepository;

    private Object goDeeper(String url) {

        Map<String, Object> map = new HashMap<>();
        url = "https://www.ss.lv" + url;

        try {
            Document doc = Jsoup.connect(url).get();

            if(doc.select(".filter_second_line_dv").size() > 0) {
                int count = readPages(url, 1, 1);
                logger.info("Page count : " + String.valueOf(count));
                return allAdvertisements;
            } else {
                Elements links = doc.select("tbody h4 a");

                for(Element link : links) {
                    String href = link.attr("href");
                    String title = link.text();

                    if( title == "Visi sludinājumi") continue;
                    map.put(title, goDeeper(href));
                }
            }

        } catch (Exception e) {
            return new HashMap<>();
        }

        return map;
    }

    private void printElement(Object object){
        if (object.getClass().isArray()) {
            AdvertisementEntity[] advs = (AdvertisementEntity[]) object;
            for(AdvertisementEntity adv : advs){
//                logger.info(adv.toString());
            }
        } else if (object instanceof Map) {
            Map map = (Map) object;
            map.forEach((key, value) -> {
                logger.info("Nosasukums: " + key);
                printElement(value);
            });
        } else if (object instanceof List) {
            List<AdvertisementEntity[]> advs = (List<AdvertisementEntity[]>) object;
            for(AdvertisementEntity[] adv : advs){
                printElement(adv);
            }
        }  else {
            System.out.println("==================================================Something else: " + object.getClass());
        }
    }

    @Override
    public String fetchPage() {
//        LinkedList<String> startEndpoints = new LinkedList<>(Arrays.asList("/lv/real-estate/flats/"));
        LinkedList<String> startEndpoints = new LinkedList<>(Arrays.asList(
                "/lv/real-estate/flats/riga/dzeguzhkalns",
                "/lv/real-estate/flats/riga/agenskalns",
                "/lv/real-estate/flats/riga/teika"
        ));
        LinkedList<String> endpointTitles = new LinkedList<>(Arrays.asList("flats", "agenskalns", "teika"));

        Map<String,Object> map = new HashMap<>();

        try {

            for (int i = 0; i < Math.min(startEndpoints.size(), endpointTitles.size()); i++) {
                map.put(endpointTitles.get(i),goDeeper(startEndpoints.get(i)));
            }

            printElement(map);

            return null;
        } catch (Exception e) {
            return "Failed to scrape: " + e.getMessage();
        }
    }

//    private int getPageCount(String url, int lives, int nextPage) throws IOException {
//        Document doc = Jsoup.connect(url + "/page" + nextPage +".html").get();
//        Elements pageHref = doc.select("head link");
//        int pageCounter = 0;
//
//        boolean isFirstPage = pageHref.get(0).attr("href").contains("page1.html");
//        if (isFirstPage && lives == 0){
//            return 0;
//        } else if (isFirstPage && lives >= 1){
//            nextPage++;
//            pageCounter = readPages(url, 0, nextPage) + 1;
//        } else if(!isFirstPage && lives == 0){
//            nextPage++;
//            pageCounter = readPages(url, 0, nextPage) + 1;
//        }
//
//        return pageCounter;
//    }

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

                advertisementRepository.save(advertisements[index]);
            }

            return advertisements;
        } catch (Exception e) {
            return advertisements;
        }
    }
}
