package com. rolands. ss_lv_scraping_service. controller;

import com.rolands.ss_lv_scraping_service.service.IScraperService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rolands Bidzans
 */

@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class ScraperController {

    private IScraperService iScraperService;

    @GetMapping("/scrape")
    public ResponseEntity fetchAccountDetails() {
        String url = "https://www.ss.lv/lv/real-estate/flats/gulbene-and-reg/";

        try {
            String somethinImportant = iScraperService.fetchPage();
            return ResponseEntity.status(HttpStatus.OK).body(somethinImportant);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body("Failed to scrape: " + e.getMessage());
        }

    }

}

