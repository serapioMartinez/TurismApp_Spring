package com.radical3d.turismapp.TurismApp.repository;

import org.springframework.stereotype.Repository;

import com.radical3d.turismapp.TurismApp.model.Dishes;

@Repository
public interface IDishesRepository extends IGenericItemRepository<Dishes, Integer>{
    
}
