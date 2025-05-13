package com. rolands. ss_lv_scraping_service. controller;

import com.rolands.ss_lv_scraping_service.service.IScraperService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Rolands Bidzans
 */

//@RestController
//@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
//@AllArgsConstructor
//@Validated
//public class ScraperController {
//
//    private IScraperService iScraperService;
//
//    @GetMapping("/scrape")
//    public ResponseEntity fetchAccountDetails() {
//        String url = "https://www.ss.lv/lv/real-estate/flats/gulbene-and-reg/";
//
//        try {
//            iScraperService.fetchPage();
//            return ResponseEntity.status(HttpStatus.OK).body("OK");
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.OK).body("Failed to scrape: " + e.getMessage());
//        }
//
//    }
//
//}


@Component
@AllArgsConstructor
public class ScraperRunner implements CommandLineRunner {

    private IScraperService iScraperService;

    @Override
    public void run(String... args) throws Exception {
        String url = "https://www.ss.lv/lv/real-estate/flats/gulbene-and-reg/";

        try {
            iScraperService.fetchPage();


        } catch (Exception e) {
            System.out.println("ERROR IN RUNNER");
        }
    }
}

