package com.radical3d.turismapp.TurismApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "directions")
public class Direction {

    @Id()
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapsId()
    @JsonIgnore
    private Establishment establishment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city", referencedColumnName = "id")
    @JsonIgnore
    private City city;

    @Column(columnDefinition = "varchar(50) not null")
    private String street;

    @Column(columnDefinition = "varchar(50) not null")
    private String colony;

    @Column(columnDefinition = "varchar(5) not null")
    private String postalCode;

    @Column(columnDefinition = "varchar(5) not null default 'SN'")
    private String number = "SN";

    public void updateItemFields(Direction target, Direction origin) {
        target.setColony(origin.getColony());
        target.setNumber(origin.getNumber());
        target.setPostalCode(origin.getPostalCode());
        target.setStreet(origin.getStreet());
    }

    public void validateItemForPersistance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateItemForPersistance'");
    }
}
