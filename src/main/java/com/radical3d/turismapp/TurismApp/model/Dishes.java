package com.radical3d.turismapp.TurismApp.model;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "dishes")
public class Dishes extends CityItemSuperClass{

    @Builder
    public Dishes(int id, City city, String name, String description, String photo){
        super(id, city, name, description, photo);
    }

    @Override
    public void updateItemFields(CityItemSuperClass target, CityItemSuperClass origin) {
        Dishes dishTg = (Dishes) target;
        Dishes dishOr = (Dishes) origin;

        dishTg.setName(dishOr.getName());
        dishTg.setDescription(dishOr.getDescription());
        dishTg.setPhoto(dishOr.getPhoto());
    }

    @Override
    public void validateItemForPersistance() {
        // Implement validations
    }
}
