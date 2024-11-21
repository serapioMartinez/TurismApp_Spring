package com.radical3d.turismapp.TurismApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.radical3d.turismapp.TurismApp.model.City;

@Repository
public interface ICityRepository extends JpaRepository<City, Integer>{
    
    Optional<City> findById(int id);
    Optional<City> findByUserAdmin_User_Username(String username);

    @Query(value = "SELECT c.userAdmin.id from City c where c.id = ?1")
    Optional<Integer> findCityAdminId(int cityID);

    @Query(value = "select c.id from City c where c.userAdmin.user.username=?1")
    Optional<Integer> findCityIDByUsername(String username);
}
