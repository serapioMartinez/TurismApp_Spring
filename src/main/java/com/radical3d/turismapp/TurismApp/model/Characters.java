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
@Entity()
@Table(name = "characters")
public class Characters extends CityItemSuperClass{ 

    private short deathYear;
    private short birthYear;

    @Builder
    public Characters(int id, City city, String name, String description, short deathYear, short birthYear, String photo) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.description = description;
        this.deathYear = deathYear;
        this.birthYear = birthYear;
        this.photo = photo;
    }

    @Override
    public Characters clone() {
        try{
            return (Characters) super.clone();
        } catch(CloneNotSupportedException e){
            return Characters.builder()
                    .city(this.city)
                    .name(this.name)
                    .description(this.description)
                    .deathYear(this.deathYear)
                    .birthYear(this.birthYear)
                    .photo(this.photo)
                    .build();
        }
    }

    @Override
    public void updateItemFields(CityItemSuperClass target, CityItemSuperClass origin) {
        Characters charTg = (Characters) target;
        Characters charOr = (Characters) origin;

        charTg.setName(charOr.getName());
        charTg.setPhoto(charOr.getPhoto());
        charTg.setBirthYear(charOr.getBirthYear());
        charTg.setDeathYear(charOr.getDeathYear());
        charTg.setDescription(charOr.getDescription());
    }

    @Override
    public void validateItemForPersistance() {
        //Implement validations
    }

    

}
