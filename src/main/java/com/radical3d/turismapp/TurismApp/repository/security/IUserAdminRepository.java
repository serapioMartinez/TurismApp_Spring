package com.radical3d.turismapp.TurismApp.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.radical3d.turismapp.TurismApp.model.security.UserAdmin;

@Repository
public interface IUserAdminRepository extends JpaRepository<UserAdmin, Integer>{
    Optional<UserAdmin> findByUser_username(String username);

    @Query(nativeQuery = false, value = "select u.userType from UserAdmin u where u.user.username = ?1")
    Optional<Character> getuserTypeByUsername(String username);
}
