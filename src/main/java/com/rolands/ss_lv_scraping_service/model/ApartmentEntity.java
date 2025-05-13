package com.rolands.ss_lv_scraping_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Table(name="apartments")
public class ApartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "apartment_id")
    private UUID apartmentId;

    @JoinColumn(name = "district_id")
    private UUID district;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "room_count", nullable = false)
    private int roomCount;

    @Column(name = "area", nullable = false)
    private double area;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Column(name = "series", nullable = false)
    private String series;

    @ManyToOne
    @JoinColumn(name = "real_estate_type", nullable = false)
    private RealEstateTypeEntity realEstateType;
}