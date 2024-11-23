package com.radical3d.turismapp.TurismApp.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.radical3d.turismapp.TurismApp.model.Characters;
import com.radical3d.turismapp.TurismApp.model.City;
import com.radical3d.turismapp.TurismApp.repository.ICharactersRepository;
import com.radical3d.turismapp.TurismApp.repository.ICityRepository;
import com.radical3d.turismapp.TurismApp.security.SecurityUtils;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {

    @Mock
    private SecurityUtils securityUtils;

    @Mock
    private ICityRepository cityRepository;

    @Mock
    private ICharactersRepository charactersRepository;

    @InjectMocks
    private CharacterService characterService;

    private City city;
    private Characters character;
    private final int CITY_ID = 1;
    private final int CHARACTER_ID = 1;

    @BeforeEach
    void init() {
        city = City.builder()
                .id(CITY_ID)
                .name("name")
                .build();

        character = Characters.builder()
                .id(CHARACTER_ID)
                .name("Name")
                .description("Description")
                .build();
    }

    @Test
    void testCreateItem() throws Exception {

        when(securityUtils.getcityIDForAuthenticatedUser()).thenReturn(CITY_ID);
        when(cityRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(city));
        when(charactersRepository.save(character)).thenReturn(character);

        Characters c_character = characterService.createItem(character);

        assertAll(
                () -> assertNotNull(c_character),
                () -> assertEquals(CHARACTER_ID ,c_character.getId()),
                () -> assertEquals(city, c_character.getCity()));
    }

    @Test
    void testDeleteItem() throws Exception{
        character.setCity(city);

        when(securityUtils.getcityIDForAuthenticatedUser()).thenReturn(CITY_ID);
        when(cityRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(city));
        when(charactersRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(character));

        Characters d_character = characterService.deleteItem(CHARACTER_ID);

        assertAll(
            () -> assertNotNull(d_character),
            () -> assertEquals(CHARACTER_ID, d_character.getId())
        );

    }

    @Test
    void testGetCityitems() throws Exception{
        when(securityUtils.getcityIDForAuthenticatedUser()).thenReturn(CITY_ID);
        when(cityRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(city));
        when(charactersRepository.findAllByCity_Id(Mockito.anyInt(), Mockito.any())).thenReturn(List.of(character));

        List<Characters> characters = characterService.getCityitems(new Response(),0, "id", false);

        assertAll(
            () -> assertNotNull(characters),
            () -> assertTrue(characters.size()>0)
        );
    }

    @Test
    void testGetItem() {
        when(charactersRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(character));

        Characters c_Characters = characterService.getItem(CHARACTER_ID);

        assertAll(
            () -> assertNotNull(c_Characters),
            () -> assertEquals(CHARACTER_ID, c_Characters.getId())
        );
    }

    @Test
    void testUpdateItem() throws Exception{
        
        character.setCity(city);

        when(securityUtils.getcityIDForAuthenticatedUser()).thenReturn(CITY_ID);
        when(cityRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(city));
        when(charactersRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(character));
        when(charactersRepository.save(character)).thenReturn(character);

        Characters charOr = Characters.builder()
                                .id(CHARACTER_ID)
                                .name("New name")
                                .description("New Description")
                                .build();
        
        Characters u_character = characterService.updateItem(charOr);

        assertAll(
            () -> assertNotNull(u_character),
            () -> assertEquals(character, u_character),
            () -> assertEquals(CHARACTER_ID, u_character.getId()),
            () -> assertEquals(charOr.getName(), u_character.getName()),
            () -> assertEquals(charOr.getDescription(), u_character.getDescription())
        );
    }
}
