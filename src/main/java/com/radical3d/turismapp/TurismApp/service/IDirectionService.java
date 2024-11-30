package com.radical3d.turismapp.TurismApp.service;

import com.radical3d.turismapp.TurismApp.model.Direction;
import com.radical3d.turismapp.TurismApp.model.Establishment;

public interface IDirectionService {
    public Direction createEstablishmentDirection(Direction direction, int establishmentID);
    public Direction createEstablishmentDirection(Direction direction, Establishment establishment);
    public Direction updateEstablishmentDirection(Direction direction, int establishmentID);
    public Direction updateEstablishmentDirection(Direction direction, Establishment establishment);
}
