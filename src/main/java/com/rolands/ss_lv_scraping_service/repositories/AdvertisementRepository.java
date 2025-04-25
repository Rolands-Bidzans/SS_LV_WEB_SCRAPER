package com.rolands.ss_lv_scraping_service.repositories;

import com.rolands.ss_lv_scraping_service.model.AdvertisementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long> {

//    Optional<AccountsEntity> findByEmail(String email);

//    @Transactional
//    @Modifying
//    void deleteByAccountNumber(String accountNumber);

}
