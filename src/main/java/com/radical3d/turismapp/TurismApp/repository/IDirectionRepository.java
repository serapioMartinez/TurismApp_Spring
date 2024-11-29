package com.radical3d.turismapp.TurismApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radical3d.turismapp.TurismApp.model.Direction;

@Repository
public interface IDirectionRepository extends JpaRepository<Direction, Integer>{

}
