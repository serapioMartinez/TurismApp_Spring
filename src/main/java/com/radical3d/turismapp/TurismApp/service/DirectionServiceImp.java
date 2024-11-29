package com.radical3d.turismapp.TurismApp.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.model.City;
import com.radical3d.turismapp.TurismApp.model.Direction;
import com.radical3d.turismapp.TurismApp.model.Establishment;
import com.radical3d.turismapp.TurismApp.repository.ICityRepository;
import com.radical3d.turismapp.TurismApp.repository.IDirectionRepository;
import com.radical3d.turismapp.TurismApp.repository.IEstablishmentRepository;
import com.radical3d.turismapp.TurismApp.security.SecurityUtils;

@Service
public class DirectionServiceImp implements IDirectionService {

    @Autowired
    private ICityRepository cityRepository;

    @Autowired
    private IDirectionRepository directionRepository;

    @Autowired
    private IEstablishmentRepository establishmentRepository;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public Direction createEstablishmentDirection(Direction direction, int establishmentID) {
        City city = getUserCity();
        Establishment establishment = establishmentRepository
                .findById(establishmentID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found"));
        if (city.getId() != establishment.getCity().getId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Current user cannot modify the record");
        direction.setCity(city);
        direction.setEstablishment(establishment);

        return directionRepository.save(direction);
    }

    @Override
    public Direction updateEstablishmentDirection(Direction direction, int establishmentID) {
        City city = getUserCity();
        Establishment establishment = establishmentRepository
                .findById(establishmentID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found"));
        if (city.getId() != establishment.getCity().getId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Current user cannot modify the record");
        Direction p_direction = establishment.getDirection();
        if (p_direction == null) {
            direction.setCity(city);
            direction.setEstablishment(establishment);

            return directionRepository.save(direction);
        }
        p_direction.updateItemFields(p_direction, direction);
        return directionRepository.save(p_direction);
    }

    private City getUserCity() {
        try {
            int cityID = securityUtils.getcityIDForAuthenticatedUser();
            return cityRepository.findById(cityID).get();
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has no managed city yet");
        }
    }

}
