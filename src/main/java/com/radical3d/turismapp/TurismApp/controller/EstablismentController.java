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
import com.radical3d.turismapp.TurismApp.model.Direction;
import com.radical3d.turismapp.TurismApp.model.Establishment;
import com.radical3d.turismapp.TurismApp.service.EstablishmentService;
import com.radical3d.turismapp.TurismApp.service.IDirectionService;
import com.radical3d.turismapp.TurismApp.utils.AppUtils;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/city/establishments")
public class EstablismentController {

    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    private IDirectionService directionService;

    @PostMapping
    public ResponseEntity<Establishment> createEstablisment(@RequestBody Establishment establishment){
        return ResponseEntity.ok().body(establishmentService.createItem(establishment));
    }

    @PutMapping
    public ResponseEntity<Establishment> updateEstablishment(@RequestBody Establishment establishment){
        return ResponseEntity.ok().body(establishmentService.updateItem(establishment));
    }

    @DeleteMapping
    public ResponseEntity<Establishment> deleteEstablishment(@RequestParam(required = true) int id){
        return ResponseEntity.ok().body(establishmentService.deleteItem(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establishment> getEstablishment(@PathVariable(required = true) int id){
        return ResponseEntity.ok().body(establishmentService.getItem(id));
    }

    @GetMapping
    public ResponseEntity<List<Establishment>> getCityEstablishmentsByPage(
        HttpServletResponse response,
        @RequestParam(required = true, defaultValue = "1") int page,
        @RequestParam(required = true, defaultValue = "id") String orderBy,
        @RequestParam(required = true, defaultValue = "true") boolean ascending
    ){
        if(page<1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specified page: "+page+" invalid");

        orderBy = AppUtils.validateSortingFieldExist(Establishment.class, orderBy, "id");
        return ResponseEntity.ok().body(establishmentService.getCityitems(response, page-1, orderBy, ascending));
    }

    @PostMapping(path = "/direction")
    public ResponseEntity<Direction> createEstablishmentDirection(
        @RequestBody Direction direction){
        directionRequestHaveID(direction);
        return ResponseEntity.ok().body(directionService.createEstablishmentDirection(direction));
    }

    @PutMapping(path = "/direction")
    public ResponseEntity<Direction> updateEstablishmentDirection(
        @RequestBody Direction direction){
        directionRequestHaveID(direction);
        return ResponseEntity.ok().body(directionService.updateEstablishmentDirection(direction));
    }

    private void directionRequestHaveID(Direction direction){
        if (direction.getId() <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide a valid ID for the direction");
    }
}
