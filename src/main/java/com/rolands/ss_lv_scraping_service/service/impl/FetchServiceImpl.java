package com.rolands.ss_lv_scraping_service.service.impl;

import com.rolands.ss_lv_scraping_service.dto.CityTransactionStats;
import com.rolands.ss_lv_scraping_service.repositories.AdvertisementRepository;
import com.rolands.ss_lv_scraping_service.service.IFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchServiceImpl implements IFetchService {
    @Autowired
    AdvertisementRepository advertisementRepository;


    @Override
    public List<CityTransactionStats> fetchCityTransactionStats() {
        return advertisementRepository.getLatviaTransactionStats();
    }
}
