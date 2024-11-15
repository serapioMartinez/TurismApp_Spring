package com.radical3d.turismapp.TurismApp.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.radical3d.turismapp.TurismApp.model.Characters;
import com.radical3d.turismapp.TurismApp.utils.LoggerHelper;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ICharactersRepositoryTest extends SharedCityInitialization{

    @Autowired
    private ICharactersRepository charactersRepository;

    private final int COUNT = 5;

    @BeforeEach
    void init(){
        LoggerHelper.info("Creating Characters for Testing ...");
        Characters character = Characters.builder()
                                .name("Character")
                                .description("Description")
                                .birthYear((short)1900)
                                .deathYear((short)1995)
                                .city(c_city)
                                .build();
        for(int i=0;i<COUNT;i++){
            try{
                charactersRepository.save(character.clone());
            }catch(Exception e ){
                e.printStackTrace();
            }
        }
        LoggerHelper.info("Character creation finilized ...");
    }

    @Test
    void testCountByCity_Id() {
        int count_city = charactersRepository.countByCity_Id(c_city.getId());

        assertEquals(COUNT, count_city);
    }

    @Test
    void testCountByCity_Id_NoCity() {
        int count_city = charactersRepository.countByCity_Id(0);

        assertEquals(0, count_city);
    }

    @Test
    void testFindAllByCity_Id() {
        List characters = charactersRepository.findAllByCity_Id(c_city.getId(), PageRequest.ofSize(COUNT+10)); //+10 correspond to a offset

        assertAll(
            () -> assertFalse(characters.isEmpty()),
            () -> assertEquals(COUNT, characters.size())
        );
    }
}
