package com.radical3d.turismapp.TurismApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radical3d.turismapp.TurismApp.model.City;
import com.radical3d.turismapp.TurismApp.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController()
@RequestMapping("/api/v1/city")
public class CityController {

    @Autowired
    ICityService cityService;

    @PostMapping()
    public ResponseEntity<City> createUserCity(@RequestBody City city) throws Exception{
        City generated_ciudad = cityService.createCity(city);
        
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(generated_ciudad);
    }

    @GetMapping()
    public ResponseEntity<City> getUserCity() {
        City city = cityService.getCity();
        return ResponseEntity.ok(city);
    }

    @PatchMapping()
    public ResponseEntity<City> patchUserCiudad(@RequestBody City city){
        return ResponseEntity.ok().body(cityService.updateCity(city));
    }   

    @DeleteMapping()
    public ResponseEntity<City> deleteCiudad(){
        return ResponseEntity.ok().body(cityService.deleteCity());
    }

    
}
