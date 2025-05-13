package com.rolands.ss_lv_scraping_service.repositories;


import com.rolands.ss_lv_scraping_service.dto.AreaStatistics;
import com.rolands.ss_lv_scraping_service.dto.CityTransactionStats;
import com.rolands.ss_lv_scraping_service.model.AdvertisementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, UUID> {

//    Optional<AccountsEntity> findByEmail(String email);

//    @Transactional
//    @Modifying
//    void deleteByAccountNumber(String accountNumber);

    @Query(value = """
        SELECT COUNT(*) AS count, c.city_name AS cityName, adv.transaction_type AS transactionType
        FROM advertisements adv
        LEFT JOIN apartments ap ON adv.apartment_id = ap.apartment_id
        LEFT JOIN districts d ON d.district_id = ap.district
        LEFT JOIN cities c ON c.city_id = d.city_id
        GROUP BY c.city_id, c.city_name, adv.transaction_type
        """, nativeQuery = true)
    List<CityTransactionStats> getLatviaTransactionStats();

    @Query(value = """
        SELECT COUNT(*) AS count, ROUND(AVG(adv.price)::numeric, 0) AS price, adv.transaction_type AS transactionType
        FROM advertisements adv
        GROUP BY adv.transaction_type
        """, nativeQuery = true)
    List<AreaStatistics> getLatviaTransactionAverageStats();


    @Query(value = """
        SELECT 
            c.city_name AS cityName, 
            adv.transaction_type AS transactionType, 
            COUNT(*) AS count
        FROM advertisements adv
        LEFT JOIN apartments ap ON adv.apartment_id = ap.apartment_id
        LEFT JOIN districts d ON ap.district = d.district_id
        LEFT JOIN cities c ON d.city_id = c.city_id
        WHERE c.city_name IN (:cities)
        GROUP BY c.city_name, adv.transaction_type
    """, nativeQuery = true)
    List<CityTransactionStats> getTransactionStats(@Param("cities") List<String> cities);




}
