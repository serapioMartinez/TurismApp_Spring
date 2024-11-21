package com.radical3d.turismapp.TurismApp.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.radical3d.turismapp.TurismApp.repository.ITourismTypeRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity()
@Table(name = "tourism_type")
public class TourismType {
    @Id
    @NonNull
    @Column(columnDefinition = "varchar(2) not null", unique = true)
    private String id;

    @Column(columnDefinition = "varchar(50)")
    private String type;

    // public TourismType(String id){
    //     this.id = id;

    //     @Autowired
    //     ITourismTypeRepository tourismTypeRepository;



    // }
}
