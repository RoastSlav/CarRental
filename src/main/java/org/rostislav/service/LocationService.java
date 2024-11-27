package org.rostislav.service;

import java.util.Collection;

import org.rostislav.models.Location;
import org.rostislav.models.LocationWithCity;
import org.rostislav.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Collection<LocationWithCity> getAllLocationsWithCityNames() {
        return locationRepository.getAllLocationsWithCityNames();
    }

    public void addLocation(String name, String address, int cityId, String zipCode) {
        locationRepository.addLocation(new Location(name, address, cityId, zipCode));
    }

    public void updateLocation(int id, String name, String address, int cityId, String zipCode) {
        locationRepository.updateLocation(new Location(name, address, cityId, zipCode) {{
            setId(id);
        }});
    }

    public Collection<Location> getAllLocations() {
        return locationRepository.getAllLocations();
    }

    public void deleteLocation(int id) {
        locationRepository.deleteLocation(id);
    }
}