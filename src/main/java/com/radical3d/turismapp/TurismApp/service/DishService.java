package com.radical3d.turismapp.TurismApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radical3d.turismapp.TurismApp.configuration.AppHeadersBean;
import com.radical3d.turismapp.TurismApp.model.Dishes;
import com.radical3d.turismapp.TurismApp.repository.ICityRepository;
import com.radical3d.turismapp.TurismApp.repository.IDishesRepository;
import com.radical3d.turismapp.TurismApp.security.SecurityUtils;

@Service
public class DishService extends GenericServiceImpl<Dishes> {

    @Autowired
    private AppHeadersBean appHeaders;

    public DishService(
            @Autowired IDishesRepository dishesRepository,
            @Autowired ICityRepository cityRepository,
            @Autowired SecurityUtils securityUtils) {
        super(dishesRepository, cityRepository, securityUtils);
    }

    @Override
    public String get_HEADER_PAGINATION_LIMIT_NAME() {
        return this.appHeaders.getHEADER_PAGINATION_LIMIT_NAME();
    }

    @Override
    public String get_HEADER_PAGINATION_COUNT_NAME() {
        return this.appHeaders.getHEADER_PAGINATION_COUNT_NAME();
    }

    @Override
    public String get_HEADER_PAGINATION_PAGE_NAME() {
        return this.appHeaders.getHEADER_PAGINATION_PAGE_NAME();
    }
}
