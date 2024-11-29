package com.radical3d.turismapp.TurismApp.service;

import com.radical3d.turismapp.TurismApp.model.Direction;

public interface IDirectionService {
    public Direction createEstablishmentDirection(Direction direction, int establishmentID);
    public Direction updateEstablishmentDirection(Direction direction, int establishmentID);
}
