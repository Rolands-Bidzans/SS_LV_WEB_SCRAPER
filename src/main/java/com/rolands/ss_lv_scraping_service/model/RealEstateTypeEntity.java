package com.rolands.ss_lv_scraping_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Table(name="real_estate_types")
public class RealEstateTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "real_estate_type_id")
    private Integer realEstateTypeId;

    @Column(name = "real_estate_type_name", nullable = false)
    private String realEstateTypeName;

    @OneToMany(mappedBy = "realEstateType")
    private Set<ApartmentEntity> apartments;
}