package org.rostislav.service;

import org.rostislav.models.CarColor;
import org.rostislav.repository.CarColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CarService {
    @Autowired
    private CarColorRepository carColorRepository;

    public void addCarColor(String colorName) {
        carColorRepository.addCarColor(new CarColor(colorName));
    }

    public CarColor getColorById(int id) {
        return carColorRepository.getColorById(id);
    }
}
