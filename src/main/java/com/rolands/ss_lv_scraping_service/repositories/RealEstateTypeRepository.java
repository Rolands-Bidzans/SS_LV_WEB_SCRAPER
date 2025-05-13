package com.rolands.ss_lv_scraping_service.repositories;

import com.rolands.ss_lv_scraping_service.model.RealEstateTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealEstateTypeRepository extends JpaRepository<RealEstateTypeEntity, Integer> {
        Optional<RealEstateTypeEntity> findByRealEstateTypeName(String name);
}
