package com.radical3d.turismapp.TurismApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radical3d.turismapp.TurismApp.model.Characters;

@Repository
public interface ICharactersRepository extends IGenericItemRepository<Characters, Integer>, JpaRepository<Characters, Integer> {
    
    
}
