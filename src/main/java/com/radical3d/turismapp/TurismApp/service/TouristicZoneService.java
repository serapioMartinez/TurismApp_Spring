package com.radical3d.turismapp.TurismApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.radical3d.turismapp.TurismApp.model.TouristicZones;
import com.radical3d.turismapp.TurismApp.repository.ICityRepository;
import com.radical3d.turismapp.TurismApp.repository.ITouristicZonesRepository;
import com.radical3d.turismapp.TurismApp.security.SecurityUtils;

@Service
public class TouristicZoneService extends GenericServiceImpl<TouristicZones>{

    @Value("${application.header-pag-count-name}")
    private String HEADER_PAGINATION_COUNT_NAME;

    @Value("${application.header-pag-count-name}")
    private String HEADER_PAGINATION_PAGE_NAME;

    @Value("${application.header-pag-count-name}")
    private String HEADER_PAGINATION_LIMIT_NAME;

    public TouristicZoneService(
        @Autowired ITouristicZonesRepository touristicZonesRepository,
        @Autowired ICityRepository cityRepository,
        @Autowired SecurityUtils securityUtils){
        super(touristicZonesRepository, cityRepository, securityUtils);
    }

    @Override
    public String get_HEADER_PAGINATION_LIMIT_NAME() {
        return this.HEADER_PAGINATION_LIMIT_NAME;
    }

    @Override
    public String get_HEADER_PAGINATION_COUNT_NAME() {
        return this.HEADER_PAGINATION_COUNT_NAME;
    }

    @Override
    public String get_HEADER_PAGINATION_PAGE_NAME() {
        return this.HEADER_PAGINATION_PAGE_NAME;
    }
}
