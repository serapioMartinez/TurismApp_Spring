package com.radical3d.turismapp.TurismApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "establishments")
public class Establishment extends CityItemSuperClass {

    @Column(columnDefinition = "VARCHAR(2) NOT NULL default 'NT'")
    private String type;

    @Column(columnDefinition = "VARCHAR(10)")
    private String phoneNumber;

    @Column(columnDefinition = "VARCHAR(100)")
    private String mail;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "establishment", optional = true)
    private Direction direction;

    @Builder
    public Establishment(
            int id,
            City city,
            String name,
            String description,
            String photo,
            String type,
            String phoneNumber,
            String mail,
            Direction direction) {
        super(id, city, name, description, description);
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.direction = direction;
    }

    @Override
    public void updateItemFields(CityItemSuperClass target, CityItemSuperClass origin) {
        Establishment estTg = (Establishment) target;
        Establishment estOr = (Establishment) origin;
        estTg.setDescription(estOr.getDescription());
        estTg.setName(estOr.getName());
        estTg.setPhoto(estOr.getPhoto());
        estTg.setMail(estOr.getMail());
        estTg.setPhoneNumber(estOr.getPhoneNumber());
        estTg.setType(estOr.getType());
    }

    @Override
    public void validateItemForPersistance() {
    }

    public void setPhoneNumber(String phonenumber){
        this.phoneNumber = phonenumber.replace("-", "");
    }

}
