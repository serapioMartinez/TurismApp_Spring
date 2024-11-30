package com.radical3d.turismapp.TurismApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.configuration.AppHeadersBean;
import com.radical3d.turismapp.TurismApp.model.Direction;
import com.radical3d.turismapp.TurismApp.model.Establishment;
import com.radical3d.turismapp.TurismApp.repository.ICityRepository;
import com.radical3d.turismapp.TurismApp.repository.IEstablishmentRepository;
import com.radical3d.turismapp.TurismApp.security.SecurityUtils;

@Service
public class EstablishmentService extends GenericServiceImpl<Establishment>{

    @Autowired
    private AppHeadersBean appHeaders;

    @Autowired 
    private IDirectionService directionService;
 
    private IEstablishmentRepository establishmentRepository;
    
    private ICityRepository cityRepository; 
     
    private SecurityUtils securityUtils;

    public EstablishmentService(
        @Autowired IEstablishmentRepository establishmentRepository,
        @Autowired ICityRepository cityRepository, 
        @Autowired SecurityUtils securityUtils) {
        super(establishmentRepository, cityRepository, securityUtils);
        
        this.establishmentRepository = establishmentRepository;
        this.cityRepository = cityRepository;
        this.securityUtils = securityUtils;
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

    @Override
    public Establishment updateItem(Establishment item) {
        Establishment p_item = establishmentRepository.findById(item.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Item with ID: " + item.getId() + " not found"));

        validateItemIsOwnedByUser(p_item);

        p_item.updateItemFields(p_item, item);
        p_item.validateItemForPersistance();

        if(item.getDirection()!=null) p_item.setDirection(directionService.updateEstablishmentDirection(item.getDirection(), p_item));

        return establishmentRepository.save(p_item);
    }

    @Override
    public Establishment createItem(Establishment item) {
        item.setCity(getUserCity());
        
        item.validateItemForPersistance();
        if (item.getDirection() != null){
            Direction direction = item.getDirection();
            item.setDirection(directionService.createEstablishmentDirection(direction, item));

            return establishmentRepository.save(item);
        }
        return establishmentRepository.save(item);
    }

}
