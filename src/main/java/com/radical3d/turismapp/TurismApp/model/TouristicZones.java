package com.radical3d.turismapp.TurismApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "touristic_zone")
public class TouristicZones extends CityItemSuperClass{
    @Column(columnDefinition = "VARCHAR(2)", nullable = false)
    private String zoneType;

    @Builder
    public TouristicZones(int id, City city, String name, String description, String photo, String zoneType) {
        super(id, city, name, description, photo);
        this.zoneType = zoneType;
    }

    @Override
    public void updateItemFields(CityItemSuperClass target, CityItemSuperClass origin) {
        TouristicZones zoneTg = (TouristicZones) target;
        TouristicZones zoneOr= (TouristicZones) origin;

        zoneTg.setName(zoneOr.getName());
        zoneTg.setPhoto(zoneOr.getPhoto());
        zoneTg.setDescription(zoneOr.getDescription());
        zoneTg.setZoneType(zoneOr.getZoneType());
    }

    @Override
    public void validateItemForPersistance() {
        // Implement validations
    }

    
}
