package org.rostislav.service;

import java.util.Collection;

import org.rostislav.models.City;
import org.rostislav.models.CityWithState;
import org.rostislav.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public Collection<CityWithState> getAllCitiesWithStateNames() {
        return cityRepository.getAllCitiesWithStateNames();
    }

    public void addCity(String cityName, int stateId) {
        cityRepository.addCity(new City(cityName, stateId));
    }

    public void updateCity(int id, String cityName, int stateId) {
        cityRepository.updateCity(new City(cityName, stateId) {{
            setId(id);
        }});
    }

    public void deleteCity(int id) {
        cityRepository.deleteCity(id);
    }
}