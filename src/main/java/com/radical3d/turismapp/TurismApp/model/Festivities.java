package com.radical3d.turismapp.TurismApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "festivities")
public class Festivities extends CityItemSuperClass{
    private short day;
    private short month;
}
