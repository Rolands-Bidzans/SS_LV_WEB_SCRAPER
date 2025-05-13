package com.rolands.ss_lv_scraping_service.controller;

import com.rolands.ss_lv_scraping_service.dto.AreaStatistics;
import com.rolands.ss_lv_scraping_service.dto.CityTransactionStats;
import com.rolands.ss_lv_scraping_service.repositories.AdvertisementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class FetchController {

    @Autowired
    AdvertisementRepository advertisementRepository;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/transactions/{city}")
    public List<CityTransactionStats> getStats(@PathVariable String city) {
        if(city == null) {
            return List.of();
        } else if (city.equals("Latvia")) {
            log.info("City: {}", city);
            return advertisementRepository.getLatviaTransactionStats();
        } else if (city.equals("Rīgas rajons")){
            List<String> newList = List.of("Rīga", "Rīgas rajons");
            return advertisementRepository.getTransactionStats(newList);
        }
        List<String> newList = List.of(city);
        List<CityTransactionStats> values = advertisementRepository.getTransactionStats(newList);
        values.forEach(v -> System.out.println(v));
        return values;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/transactions/price/{city}")
    public List<AreaStatistics> getPriceStats(@PathVariable String city) {
        if(city == null) {
            return List.of();
        } else if (city.equals("Latvia")) {
            return advertisementRepository.getLatviaTransactionAverageStats();
        }
//        else if (city.equals("Rīgas rajons")){
//            List<String> newList = List.of("Rīga", "Rīgas rajons");
//            return advertisementRepository.getTransactionStats(newList);
//        }
//        List<String> newList = List.of(city);
//        List<CityTransactionStats> values = advertisementRepository.getTransactionStats(newList);
//        values.forEach(v -> System.out.println(v));
        return List.of();
    }



}
