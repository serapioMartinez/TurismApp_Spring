package com.radical3d.turismapp.TurismApp.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.radical3d.turismapp.TurismApp.model.CityItemSuperClass;

@Service
public interface IGenericItemsService<T extends CityItemSuperClass> {
    public T getItem(Integer itemID);
    public List<T> getCityitems(int page, String orderBy, boolean ascending);
    public T createItem(T item);
    public T updateItem(T item);
    public T deleteItem(Integer itemID);
}