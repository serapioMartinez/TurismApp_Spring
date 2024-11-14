package com.radical3d.turismapp.TurismApp.model;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "dishes")
public class Dishes extends CityItemSuperClass{
}
