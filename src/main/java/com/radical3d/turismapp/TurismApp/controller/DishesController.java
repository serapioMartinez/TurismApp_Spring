package com.radical3d.turismapp.TurismApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.model.Dishes;
import com.radical3d.turismapp.TurismApp.service.DishService;
import com.radical3d.turismapp.TurismApp.utils.AppUtils;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/city/dishes")
public class DishesController {
    
    @Autowired
    private DishService dishService;

    @PostMapping()
    public ResponseEntity<Dishes> createDish(@RequestBody Dishes dish) {
        return ResponseEntity.ok().body(dishService.createItem(dish));
    }

    @PutMapping()
    public ResponseEntity<Dishes> updateCharacter(@RequestBody Dishes dish) {
        return ResponseEntity.ok().body(dishService.updateItem(dish));
    }    

    @GetMapping("/{id}")
    public ResponseEntity<Dishes> getCharacter(@PathParam(value = "id") Integer id) {
        return ResponseEntity.ok().body(dishService.getItem(id));
    }

    @GetMapping()
    public ResponseEntity<List<Dishes>> getCityDishesByPage(
        HttpServletResponse response,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "id") String orderBy,
        @RequestParam(defaultValue = "true") boolean ascending) {
        if (page<1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specified page: "+page+" invalid");
        }
        orderBy = AppUtils.validateSortingFieldExist(Dishes.class, orderBy, "id");
        return ResponseEntity.ok().body(dishService.getCityitems(response, page-1, orderBy, ascending));
    }

    @DeleteMapping()
    public ResponseEntity<Dishes> deleteCharacter(@RequestParam(required = true) int id){
        return ResponseEntity.ok().body(dishService.deleteItem(id));
    }
}
