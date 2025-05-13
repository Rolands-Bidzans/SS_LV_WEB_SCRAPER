package com.rolands.ss_lv_scraping_service.repositories;

import com.rolands.ss_lv_scraping_service.model.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, UUID> {
    Optional<DistrictEntity> findByDistrictName(String districtName);
}
