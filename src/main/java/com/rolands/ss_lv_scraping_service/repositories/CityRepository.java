package com.rolands.ss_lv_scraping_service.repositories;

import com.rolands.ss_lv_scraping_service.model.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, UUID> {
    Optional<CityEntity> findByCityName(String cityName);
}
