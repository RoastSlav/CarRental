package org.rostislav.service;

import java.util.Collection;

import org.rostislav.models.CarColor;
import org.rostislav.repository.CarColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarColorService {

    private final CarColorRepository carColorRepository;

    @Autowired
    public CarColorService(CarColorRepository carColorRepository) {
        this.carColorRepository = carColorRepository;
    }

    public Collection<CarColor> getAllColors() {
        return carColorRepository.getAllColors();
    }

    public void addCarColor(String colorName) {
        carColorRepository.addCarColor(new CarColor(colorName));
    }

    public void updateCarColor(int id, String colorName) {
        CarColor carColor = new CarColor(colorName);
        carColor.setId(id);
        carColorRepository.updateCarColor(carColor);
    }

    public void deleteCarColor(int id) {
        carColorRepository.deleteCarColor(id);
    }
}