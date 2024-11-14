package com.radical3d.turismapp.TurismApp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class CityItemSuperClass {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    protected int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "city", referencedColumnName = "id", unique = false)
    protected City city; 

    @Column(columnDefinition = "VARCHAR(60)")
    protected String name;

    @Column(columnDefinition = "VARCHAR(250)")
    protected String description;
    
    @Column(columnDefinition = "VARCHAR(50)")
    protected String photo;
}
