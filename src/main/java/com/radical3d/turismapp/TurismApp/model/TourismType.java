package com.radical3d.turismapp.TurismApp.model;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "tourism_type")
public class TourismType {
    @Id
    @NonNull
    @Column(columnDefinition = "varchar(2) not null")
    private String id;

    @Column(columnDefinition = "varchar(50) not null")
    private String type;
}
