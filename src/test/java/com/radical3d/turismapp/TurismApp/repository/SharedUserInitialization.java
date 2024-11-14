package com.radical3d.turismapp.TurismApp.repository;

import org.junit.jupiter.api.BeforeEach;
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
import com.radical3d.turismapp.TurismApp.utils.LoggerHelper;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class SharedUserInitialization {
    @Autowired
    protected IUserRepository userRepository;

    @Autowired
    protected IUserAdminRepository userAdminRepository;

    protected final String USERNAME = "Akagami";

    protected User c_user;
    protected UserAdmin c_UserAdmin;

    @BeforeEach
    void user_init() {
        LoggerHelper.info("SharedUserInitialization Initiated ...");

        User user = User.builder()
                .username(USERNAME)
                .role(Role.ROLE_CITY)
                .password("password")
                .build();
        c_user = userRepository.save(user);
        LoggerHelper.info("User created: " + c_user);

        UserAdmin userAdmin = UserAdmin.builder()
                .name("Luis")
                .mail("mail@example.com")
                .position("MANAGER")
                .userType('C')
                .user(c_user)
                .build();
        c_UserAdmin = userAdminRepository.save(userAdmin);
        
        LoggerHelper.info("UserAdmin created: "+c_UserAdmin);
        LoggerHelper.info("SharedUserInitialization Finilized ...");
    }
}
