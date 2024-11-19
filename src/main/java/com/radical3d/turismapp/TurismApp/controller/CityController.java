package com.radical3d.turismapp.TurismApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController()
@RequestMapping("/api/v1/city")
public class CityController {

    @GetMapping()
    public String getCity() {
        return "This is the city endpoint";
    }
    
}
