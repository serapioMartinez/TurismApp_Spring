package com.radical3d.turismapp.TurismApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radical3d.turismapp.TurismApp.model.Festivities;

@Repository
public interface IFestivitiesRepsitory extends IGenericItemRepository<Festivities, Integer>, JpaRepository<Festivities, Integer>{

}
