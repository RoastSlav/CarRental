package org.rostislav.service;

import java.util.Collection;

import org.rostislav.models.CarMake;
import org.rostislav.repository.CarMakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarMakeService {

    private final CarMakeRepository carMakeRepository;

    @Autowired
    public CarMakeService(CarMakeRepository carMakeRepository) {
        this.carMakeRepository = carMakeRepository;
    }

    public CarMake getCarMakeById(int id) {
        return carMakeRepository.getCarMakeById(id);
    }

    public Collection<CarMake> getAllCarMakes() {
        return carMakeRepository.getAllCarMakes();
    }

    public void addCarMake(CarMake carMake) {
        carMakeRepository.addCarMake(carMake);
    }

    public void updateCarMake(CarMake carMake) {
        carMakeRepository.updateCarMake(carMake);
    }

    public void deleteCarMake(int id) {
        carMakeRepository.deleteCarMake(id);
    }
}
