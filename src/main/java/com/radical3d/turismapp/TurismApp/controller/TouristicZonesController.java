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

import com.radical3d.turismapp.TurismApp.model.TouristicZones;
import com.radical3d.turismapp.TurismApp.service.TouristicZoneService;
import com.radical3d.turismapp.TurismApp.utils.AppUtils;

import jakarta.servlet.http.HttpServletResponse;

@RestController()
@RequestMapping("/api/v1/city/touristicZones")
public class TouristicZonesController {
    
    @Autowired
    private TouristicZoneService zoneService;

    @PostMapping()
    public ResponseEntity<TouristicZones> createCharacter(@RequestBody TouristicZones zone) {
        return ResponseEntity.ok().body(zoneService.createItem(zone));
    }

    @PutMapping()
    public ResponseEntity<TouristicZones> updateCharacter(@RequestBody TouristicZones zone) {
        return ResponseEntity.ok().body(zoneService.updateItem(zone));
    }    

    @GetMapping("/{id}")
    public ResponseEntity<TouristicZones> getCharacter(@PathVariable Integer id) {
        return ResponseEntity.ok().body(zoneService.getItem(id));
    }

    @GetMapping()
    public ResponseEntity<List<TouristicZones>> getCityCharactersByPage(
        HttpServletResponse response,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "id") String orderBy,
        @RequestParam(defaultValue = "true") boolean ascending) {
        if (page<1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specified page: "+page+" invalid");
        }
        orderBy = AppUtils.validateSortingFieldExist(TouristicZones.class, orderBy, "id");
        return ResponseEntity.ok().body(zoneService.getCityitems(response, page-1, orderBy, ascending));
    }

    @DeleteMapping()
    public ResponseEntity<TouristicZones> deleteCharacter(@RequestParam(required = true) int id){
        return ResponseEntity.ok().body(zoneService.deleteItem(id));
    }
}
