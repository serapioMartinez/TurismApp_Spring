package com.radical3d.turismapp.TurismApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.model.Characters;
import com.radical3d.turismapp.TurismApp.service.CharacterService;
import com.radical3d.turismapp.TurismApp.utils.AppUtils;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/api/v1/city/characters")
public class CharactersController {

    @Autowired
    private CharacterService characterService;

    @PostMapping()
    public ResponseEntity<Characters> createCharacter(@RequestBody Characters character) {
        return ResponseEntity.ok().body(characterService.createItem(character));
    }

    @PutMapping()
    public ResponseEntity<Characters> updateCharacter(@RequestBody Characters characters) {
        return ResponseEntity.ok().body(characterService.updateItem(characters));
    }    

    @GetMapping("/{id}")
    public ResponseEntity<Characters> getCharacter(@PathParam(value = "id") Integer id) {
        return ResponseEntity.ok().body(characterService.getItem(id));
    }

    @GetMapping()
    public ResponseEntity<List<Characters>> getCityCharactersByPage(
        HttpServletResponse response,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "id") String orderBy,
        @RequestParam(defaultValue = "true") boolean ascending) {
        if (page<1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specified page: "+page+" invalid");
        }
        orderBy = AppUtils.validateSortingFieldExist(Characters.class, orderBy, "id");
        return ResponseEntity.ok().body(characterService.getCityitems(response, page-1, orderBy, ascending));
    }

    @DeleteMapping()
    public ResponseEntity<Characters> deleteCharacter(@RequestParam(required = true) int id){
        return ResponseEntity.ok().body(characterService.deleteItem(id));
    }
    
    
}
