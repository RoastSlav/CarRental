package org.rostislav.service;

import org.rostislav.models.City;
import org.rostislav.models.Location;
import org.rostislav.models.State;
import org.rostislav.repository.CityRepository;
import org.rostislav.repository.LocationRepository;
import org.rostislav.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;

    public void addLocation(String name, String address, int cityId, String zipCode) {
        locationRepository.addLocation(new Location(name, address, cityId, zipCode));
    }

    public Location getLocationById(int id) {
        return locationRepository.getLocationById(id);
    }

    public Collection<Location> getAllLocations() {
        return locationRepository.getAllLocations();
    }

    public void removeLocation(int id) {
        locationRepository.deleteLocation(id);
    }

    public void updateLocation(Location location) {
        locationRepository.updateLocation(location);
    }

    public void addLocation(Location location) {
        locationRepository.addLocation(location);
    }

    public State getStateById(int id) {
        return stateRepository.getStateById(id);
    }

    public Collection<State> getAllStates() {
        return stateRepository.getAllStates();
    }

    public void addState(String stateName) {
        stateRepository.addState(new State(stateName));
    }

    public void removeState(int id) {
        stateRepository.deleteState(id);
    }

    public void updateState(State state) {
        stateRepository.updateState(state);
    }

    public City getCityById(int id) {
        return cityRepository.getCityById(id);
    }

    public Collection<City> getAllCities() {
        return cityRepository.getAllCities();
    }

    public void addCity(String cityName, int stateId) {
        cityRepository.addCity(new City(cityName, stateId));
    }

    public void removeCity(int id) {
        cityRepository.deleteCity(id);
    }

    public void updateCity(City city) {
        cityRepository.updateCity(city);
    }

    public void addCity(City city) {
        cityRepository.addCity(city);
    }
}
