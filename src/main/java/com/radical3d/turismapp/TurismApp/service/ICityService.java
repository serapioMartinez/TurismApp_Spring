package com.radical3d.turismapp.TurismApp.service;

import com.radical3d.turismapp.TurismApp.model.City;

public interface ICityService {
    public City createCity(City city);
    public City updateCity(City city);
    public City getCity();
    public City deleteCity();
}
