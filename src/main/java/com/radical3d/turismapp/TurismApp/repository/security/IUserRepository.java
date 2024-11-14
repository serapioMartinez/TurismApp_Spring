package com.radical3d.turismapp.TurismApp.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radical3d.turismapp.TurismApp.model.security.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String>{
    Optional<User> findByUsername(String username);
}
