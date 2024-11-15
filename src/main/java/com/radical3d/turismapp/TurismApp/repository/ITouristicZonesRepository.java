package com.radical3d.turismapp.TurismApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radical3d.turismapp.TurismApp.model.TouristicZones;

@Repository
public interface ITouristicZonesRepository 
                    extends IGenericItemRepository<TouristicZones, Integer>, 
                            JpaRepository<TouristicZones, Integer>{

}
