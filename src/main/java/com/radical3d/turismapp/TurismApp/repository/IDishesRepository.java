package com.radical3d.turismapp.TurismApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radical3d.turismapp.TurismApp.model.Dishes;

public interface IDishesRepository extends JpaRepository<Dishes, Integer>{
    
}
