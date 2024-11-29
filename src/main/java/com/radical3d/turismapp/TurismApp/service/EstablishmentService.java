package com.radical3d.turismapp.TurismApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radical3d.turismapp.TurismApp.configuration.AppHeadersBean;
import com.radical3d.turismapp.TurismApp.model.Establishment;
import com.radical3d.turismapp.TurismApp.repository.ICityRepository;
import com.radical3d.turismapp.TurismApp.repository.IEstablishmentRepository;
import com.radical3d.turismapp.TurismApp.security.SecurityUtils;

@Service
public class EstablishmentService extends GenericServiceImpl<Establishment>{

    @Autowired
    private AppHeadersBean appHeaders;

    public EstablishmentService(
        @Autowired IEstablishmentRepository establishmentRepository,
        @Autowired ICityRepository cityRepository, 
        @Autowired SecurityUtils securityUtils) {
        super(establishmentRepository, cityRepository, securityUtils);
    }

    @Override
    public String get_HEADER_PAGINATION_LIMIT_NAME() {
        return appHeaders.getHEADER_PAGINATION_LIMIT_NAME();
    }

    @Override
    public String get_HEADER_PAGINATION_COUNT_NAME() {
        return appHeaders.getHEADER_PAGINATION_COUNT_NAME();
    }

    @Override
    public String get_HEADER_PAGINATION_PAGE_NAME() {
        return appHeaders.getHEADER_PAGINATION_PAGE_NAME();
    }

}
