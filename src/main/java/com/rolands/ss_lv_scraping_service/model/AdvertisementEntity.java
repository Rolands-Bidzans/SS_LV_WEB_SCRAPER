package com.rolands.ss_lv_scraping_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class AdvertisementEntity {
    @Id
    @Column(name="advertisement_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String advertisementId;
    private String street;
    private String roomCount;
    private String area;
    private String floor;
    private String series;
    private String price;
    private String type;
    private String city;
    private String district;
}
