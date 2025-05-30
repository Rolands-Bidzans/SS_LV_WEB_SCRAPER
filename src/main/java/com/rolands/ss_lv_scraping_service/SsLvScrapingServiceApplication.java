package com.rolands.ss_lv_scraping_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SsLvScrapingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsLvScrapingServiceApplication.class, args);
	}

}
