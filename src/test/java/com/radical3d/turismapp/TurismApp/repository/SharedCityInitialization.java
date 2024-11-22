package com.radical3d.turismapp.TurismApp.repository;

import org.junit.jupiter.api.BeforeEach;
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
public class SharedCityInitialization extends SharedUserInitialization{
    @Autowired
    protected ICityRepository cityRepository;

    protected City c_city;

    @BeforeEach
    void city_init() {
        LoggerHelper.info("SharedCityInitialization Initiated ...");

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

        LoggerHelper.info("SharedCityInitialization Finalized ...");
    }
}
