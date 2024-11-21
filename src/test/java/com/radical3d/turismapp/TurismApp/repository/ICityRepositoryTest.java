package com.radical3d.turismapp.TurismApp.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.radical3d.turismapp.TurismApp.model.City;
import com.radical3d.turismapp.TurismApp.utils.LoggerHelper;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ICityRepositoryTest extends SharedUserInitialization{

    @Autowired
    private ICityRepository cityRepository;

    private City c_city;

    @BeforeEach
    void init(){
        LoggerHelper.info("CityRepositoryTest Init");
        City city = City.builder()
                    .name("City")
                    .region("RG")
                    .municipality("Municipality")
                    .description("Description")
                    .phoneNumber("0000000000")
                    .emergencies("0000000000")
                    .userAdmin(c_UserAdmin)
                    .build();
        c_city = cityRepository.save(city);
        LoggerHelper.info("City created succesfully: "+ c_city);
    }
    @Test
    void testFindById() {
        City f_City = cityRepository.findById(c_city.getId()).get();

        assertAll(
            () -> assertNotNull(f_City),
            () -> assertEquals(c_city.getName(), f_City.getName()),
            () -> assertEquals(c_city.getUserAdmin().getName(), f_City.getUserAdmin().getName())
        );
    }

    @Test
    void testFindCityAdminId() {
        Integer adminID = cityRepository.findCityAdminId(c_city.getId()).get();

        assertAll(
            () -> assertNotNull(adminID),
            () -> assertEquals(c_city.getUserAdmin().getId(), adminID)
        );
    }

    @Test
    void testFindCityIDByUsername() {
        Integer cityID = cityRepository.findCityIDByUsername(USERNAME).get();

        assertAll(
            () -> assertNotNull(cityID),
            () -> assertEquals(c_city.getId(), cityID)
        );
    }

    @Test
    void testFindByUserAdmin_User_Username(){
        City city = cityRepository.findByUserAdmin_User_Username(USERNAME).get();

        assertAll(
            () -> assertNotNull(city),
            () -> assertEquals(c_city.getId(), city.getId()),
            () -> assertEquals(c_city.getUserAdmin().getId(), city.getUserAdmin().getId())
        );
    }
}
