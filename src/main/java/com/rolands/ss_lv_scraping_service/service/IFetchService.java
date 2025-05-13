package com.rolands.ss_lv_scraping_service.service;

import com.rolands.ss_lv_scraping_service.dto.CityTransactionStats;

import java.util.List;

public interface IFetchService {
    List<CityTransactionStats> fetchCityTransactionStats();
}
