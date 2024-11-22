package com.radical3d.turismapp.TurismApp.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.radical3d.turismapp.TurismApp.model.security.Role;
import com.radical3d.turismapp.TurismApp.model.security.User;
import com.radical3d.turismapp.TurismApp.model.security.UserAdmin;
import com.radical3d.turismapp.TurismApp.repository.security.IUserAdminRepository;
import com.radical3d.turismapp.TurismApp.repository.security.IUserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class IUserAdminRepositoryTest {

    @Autowired
    private IUserAdminRepository userAdminRepository;

    @Autowired
    private IUserRepository userRepository;

    private final String USERNAME = "Akagami";

    private User c_user;

    @BeforeEach
    void init() {
        User user = User.builder()
                .username(USERNAME)
                .role(Role.ROLE_CITY)
                .password("password")
                .build();

        c_user = userRepository.save(user);
    }

    @Test
    void saveUserAdmin_Return_CreatedObject() {
        UserAdmin userAdmin = UserAdmin.builder()
                .name("Luis")
                .mail("mail@example.com")
                .position("MANAGER")
                .userType('C')
                .user(c_user)
                .build();

        UserAdmin c_UserAdmin = userAdminRepository.save(userAdmin);
        System.out.println(c_UserAdmin);

        assertAll(
                () -> assertNotNull(c_UserAdmin),
                () -> assertTrue(c_UserAdmin.getId() > 0),
                () -> assertEquals(userAdmin.getName(), c_UserAdmin.getName()),
                () -> assertEquals(userAdmin.getUser().getUsername(), c_UserAdmin.getUser().getUsername()));
    }

    @Nested
    @DisplayName("UserAdmin_Finders")
    public class InnerIUserAdminRepositoryTest {
        private UserAdmin c_UserAdmin;

        @BeforeEach
        void init() {
            UserAdmin userAdmin = UserAdmin.builder()
                    .name("Luis")
                    .mail("mail@example.com")
                    .position("MANAGER")
                    .userType('C')
                    .user(c_user)
                    .build();

            c_UserAdmin = userAdminRepository.save(userAdmin);
        }

        @Test
        void findByUser_username_Return_UserAdmin(){
            UserAdmin f_UserAdmin = userAdminRepository.findByUser_username(USERNAME).get();

            assertAll(
                () -> assertNotNull(f_UserAdmin),
                () -> assertEquals(f_UserAdmin.getName(), f_UserAdmin.getName()),
                () -> assertEquals(f_UserAdmin.getUser().getUsername(), f_UserAdmin.getUser().getUsername())
            );

        }

        @Test
        void getuserTypeByUsername_Return_UserType(){
            Character userType = userAdminRepository.getuserTypeByUsername(USERNAME).get();

            assertAll(
                () -> assertNotNull(userType),
                () -> assertEquals('C', userType)
            );
        }
    }

}
