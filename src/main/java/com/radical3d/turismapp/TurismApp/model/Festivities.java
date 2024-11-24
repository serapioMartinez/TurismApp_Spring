package com.radical3d.turismapp.TurismApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "festivities")
public class Festivities extends CityItemSuperClass{
    private short day;
    private short month;

    @Builder
    public Festivities(int id, City city, String name, String description, String photo, short day, short month){
        super(id, city, name, description, photo);
        this.day = day;
        this.month = month;
    }

    @Override
    public void updateItemFields(CityItemSuperClass target, CityItemSuperClass origin) {
        Festivities fesTg = (Festivities) target;
        Festivities festOr = (Festivities) origin;

        fesTg.setName(festOr.getName());
        fesTg.setPhoto(festOr.getPhoto());
        fesTg.setDescription(festOr.getDescription());
        fesTg.setDay(festOr.getDay());
        fesTg.setMonth(festOr.getMonth());
    }
    @Override
    public void validateItemForPersistance() {
        // Implement validations
    }
}
