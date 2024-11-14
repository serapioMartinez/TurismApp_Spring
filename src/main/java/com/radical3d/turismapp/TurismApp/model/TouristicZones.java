package com.radical3d.turismapp.TurismApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Data
@Entity(name = "touristic_zone")
public class TouristicZones extends CityItemSuperClass{
    @Column(columnDefinition = "VARCHAR(2)", nullable = false)
    private String zoneType;

    @Builder
    public TouristicZones(City city, String name, String description,String zoneType, String photo) {
        this.city = city;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.zoneType = zoneType;
    }

    
}
