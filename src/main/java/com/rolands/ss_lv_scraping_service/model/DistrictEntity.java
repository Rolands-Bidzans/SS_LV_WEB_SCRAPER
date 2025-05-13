package com.rolands.ss_lv_scraping_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Table(name = "districts")
public class DistrictEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "district_id")
    private UUID districtId;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;

    @Column(name = "district_name", nullable = false)
    private String districtName;

    @OneToMany(mappedBy = "district")
    private Set<ApartmentEntity> apartments;
}
