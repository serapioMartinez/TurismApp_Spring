package com.radical3d.turismapp.TurismApp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericItemRepository<T, ID> extends JpaRepository<T, ID>{
    List<T> findAllByCity_Id(int id, Pageable pageable);
    
    Integer countByCity_Id(int id);
}
