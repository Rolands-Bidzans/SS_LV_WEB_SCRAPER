package com.rolands.ss_lv_scraping_service.repositories;

import com.rolands.ss_lv_scraping_service.model.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApartmentRepository extends JpaRepository<ApartmentEntity, UUID> {
}
