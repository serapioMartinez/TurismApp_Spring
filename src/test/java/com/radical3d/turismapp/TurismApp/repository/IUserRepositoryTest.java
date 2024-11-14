package com.radical3d.turismapp.TurismApp.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.radical3d.turismapp.TurismApp.model.security.Role;
import com.radical3d.turismapp.TurismApp.model.security.User;
import com.radical3d.turismapp.TurismApp.repository.security.IUserRepository;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class IUserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    @Test
    void saveUser_return_savedUser(){
        User user = User.builder()
                        .username("Akagami")
                        .role(Role.ROLE_CITY)
                        .password("password")
                        .build();

        User c_user = userRepository.save(user);

        assertAll(
            () -> assertNotNull(c_user),
            () -> assertEquals(user.getUsername(), c_user.getUsername()),
            () -> assertEquals(user.getRole(), c_user.getRole()),
            () -> assertEquals(user.getPassword(), c_user.getPassword())
        );
    }

    @Test
    void findByUsername_return_User() {
        User user = User.builder()
                        .username("Akagami")
                        .role(Role.ROLE_CITY)
                        .password("password")
                        .build();

        userRepository.save(user);

        User f_user = userRepository.findByUsername(user.getUsername()).get();

        assertAll(
            () -> assertNotNull(f_user),
            () -> assertEquals(user.getUsername(), f_user.getUsername()),
            () -> assertEquals(user.getRole(), f_user.getRole()),
            () -> assertEquals(user.getPassword(), f_user.getPassword())
        );
        
    }
}
