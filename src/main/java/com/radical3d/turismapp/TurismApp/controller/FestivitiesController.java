package com.radical3d.turismapp.TurismApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.model.Festivities;
import com.radical3d.turismapp.TurismApp.service.FestivityService;
import com.radical3d.turismapp.TurismApp.utils.AppUtils;

import jakarta.servlet.http.HttpServletResponse;

@RestController()
@RequestMapping("/api/v1/city/festivities")
public class FestivitiesController {
    
    @Autowired
    private FestivityService festivityService;

    @PostMapping()
    public ResponseEntity<Festivities> createDish(@RequestBody Festivities festivity) {
        return ResponseEntity.ok().body(festivityService.createItem(festivity));
    }

    @PutMapping()
    public ResponseEntity<Festivities> updateCharacter(@RequestBody Festivities festivity) {
        return ResponseEntity.ok().body(festivityService.updateItem(festivity));
    }    

    @GetMapping("/{id}")
    public ResponseEntity<Festivities> getCharacter(@PathVariable Integer id) {
        return ResponseEntity.ok().body(festivityService.getItem(id));
    }

    @GetMapping()
    public ResponseEntity<List<Festivities>> getCityDishesByPage(
        HttpServletResponse response,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "id") String orderBy,
        @RequestParam(defaultValue = "true") boolean ascending) {
        if (page<1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specified page: "+page+" invalid");
        }
        orderBy = AppUtils.validateSortingFieldExist(Festivities.class, orderBy, "id");
        return ResponseEntity.ok().body(festivityService.getCityitems(response, page-1, orderBy, ascending));
    }

    @DeleteMapping()
    public ResponseEntity<Festivities> deleteCharacter(@RequestParam(required = true) int id){
        return ResponseEntity.ok().body(festivityService.deleteItem(id));
    }
}
