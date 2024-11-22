package com.radical3d.turismapp.TurismApp.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.model.City;
import com.radical3d.turismapp.TurismApp.model.CityItemSuperClass;
import com.radical3d.turismapp.TurismApp.repository.ICityRepository;
import com.radical3d.turismapp.TurismApp.repository.IGenericItemRepository;
import com.radical3d.turismapp.TurismApp.security.SecurityUtils;

public class GenericServiceImpl<T extends CityItemSuperClass> implements IGenericItemsService<T> {

    private SecurityUtils securityUtils;

    private ICityRepository cityRepository;

    private IGenericItemRepository<T, Integer> itemRepository;

    private final int PAGE_SIZE = 10;

    public GenericServiceImpl(IGenericItemRepository<T,Integer> itemRepository, ICityRepository cityRepository,
            SecurityUtils securityUtils) {
        this.itemRepository = itemRepository;
        this.securityUtils = securityUtils;
        this.cityRepository = cityRepository;
    }

    @Override
    public T getItem(Integer itemID) {
        return itemRepository.findById(itemID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Item with ID: " + itemID + " not found"));
    }

    @Override
    public List<T> getCityitems(int page, String orderBy, boolean ascending) {
        City city = getUserCity();

        return itemRepository.findAllByCity_Id(
                city.getId(),
                PageRequest.of(
                        page,
                        PAGE_SIZE,
                        Sort.by(ascending ? Direction.ASC : Direction.DESC, orderBy)));
    }

    @Override
    public T createItem(T item) {
        item.setCity(getUserCity());
        return itemRepository.save(item);
    }

    @Override
    public T updateItem(T item) {
        T p_item = itemRepository.findById(item.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Item with ID: " + item.getId() + " not found"));

        validateItemIsOwnedByUser(p_item);

        p_item.updateItemFields(p_item, item);
        p_item.validateItemForPersistance();

        return itemRepository.save(p_item);
    }

    private void validateItemIsOwnedByUser(T item) {
        City city = getUserCity();
        if(item.getCity().getId() != city.getId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Item with ID: " + item.getId() + " not owned by the user");
         
    }

    @Override
    public T deleteItem(Integer itemID) {
        T item = itemRepository.findById(itemID)
                        .orElseThrow();
        validateItemIsOwnedByUser(item);

        itemRepository.delete(item);

        return item;
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
