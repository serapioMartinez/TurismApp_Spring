package com.radical3d.turismapp.TurismApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.model.City;
import com.radical3d.turismapp.TurismApp.model.security.UserAdmin;
import com.radical3d.turismapp.TurismApp.repository.ICityRepository;
import com.radical3d.turismapp.TurismApp.repository.security.IUserAdminRepository;
import com.radical3d.turismapp.TurismApp.utils.AppUtils;

@Service
public class CityService implements ICityService {

    @Autowired
    private ICityRepository cityRepository;

    @Autowired
    private IUserAdminRepository userAdminRepository;

    @Override
    public City createCity(City city) {
        if (userHasCity())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User already manage a city");
        String username = getUsernameAuthenticatedUser();
        UserAdmin userAdmin = userAdminRepository.findByUser_username(username).get();
        city.setUserAdmin(userAdmin);
        return saveCity(city);
    }

    @Override
    public City updateCity(City city) {
        if(userHasCity()){
            City u_city = cityRepository.findByUserAdmin_User_Username(getUsernameAuthenticatedUser()).get();
            updateCiudadData(u_city, city);
            return saveCity(u_city);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has no managed City yet!");
    }

    private City saveCity(City city){
        if (!City.isCiudadValidForPersistance(city))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields were not valid!");
        return cityRepository.save(city);
    }

    @Override
    public City getCity() {
        return cityRepository.findByUserAdmin_User_Username(getUsernameAuthenticatedUser())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has no managed city"));
    }

    @Override
    public City deleteCity() {
        City city = cityRepository.findByUserAdmin_User_Username(getUsernameAuthenticatedUser())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has no managed city"));
        cityRepository.delete(city);
        return City.builder()
                    .id(city.getId())
                    .name(city.getName())
                    .build();
    }

    private boolean userHasCity() {
        UserDetails userDetails = (UserDetails) AppUtils.getContextAuthentication().getPrincipal();

        return cityRepository.findCityIDByUsername(userDetails.getUsername()).isPresent() ? true : false;
    }

    private String getUsernameAuthenticatedUser() {
        return ((UserDetails) AppUtils.getContextAuthentication()
                .getPrincipal())
                .getUsername();
    }

    private void updateCiudadData(City target, City origin) {
        target.setName(origin.getName());
        target.setRating(origin.getRating());
        target.setMail(origin.getMail());
        target.setRaters(origin.getRaters());
        target.setDescription(origin.getDescription());
        target.setEmergencies(origin.getEmergencies());
        target.setPhoto(origin.getPhoto());
        target.setMunicipality(target.getMunicipality());
        target.setMagicTown(origin.isMagicTown());
        target.setRegion(origin.getRegion());
        target.setPhoneNumber(origin.getPhoneNumber());
        target.setMapsURL(origin.getMapsURL());
        target.setTourismTypes(origin.getTourismTypes());
    }

}
