package com.radical3d.turismapp.TurismApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity()
@Table(name = "characters")
public class Characters extends CityItemSuperClass{ 

    private short deathYear;
    private short birthYear;

    @Builder
    public Characters(City city, String name, String description, short deathYear, short birthYear, String photo) {
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

    

}
