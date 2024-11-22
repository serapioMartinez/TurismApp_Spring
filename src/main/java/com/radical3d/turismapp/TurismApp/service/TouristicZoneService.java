package com.radical3d.turismapp.TurismApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radical3d.turismapp.TurismApp.model.TouristicZones;
import com.radical3d.turismapp.TurismApp.repository.ICityRepository;
import com.radical3d.turismapp.TurismApp.repository.ITouristicZonesRepository;
import com.radical3d.turismapp.TurismApp.security.SecurityUtils;

@Service
public class TouristicZoneService extends GenericServiceImpl<TouristicZones>{
    public TouristicZoneService(
        @Autowired ITouristicZonesRepository touristicZonesRepository,
        @Autowired ICityRepository cityRepository,
        @Autowired SecurityUtils securityUtils
    ){
        super(touristicZonesRepository, cityRepository, securityUtils);
    }
}
