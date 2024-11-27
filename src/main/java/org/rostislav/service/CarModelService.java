package org.rostislav.service;

import java.util.Collection;

import org.rostislav.models.CarModel;
import org.rostislav.repository.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarModelService {

    private final CarModelRepository carModelRepository;

    @Autowired
    public CarModelService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    public CarModel getCarModelById(int id) {
        return carModelRepository.getCarModelById(id);
    }

    public Collection<CarModel> getAllCarModels() {
        return carModelRepository.getAllCarModels();
    }

    public void addCarModel(CarModel carModel) {
        carModelRepository.addCarModel(carModel);
    }

    public void updateCarModel(CarModel carModel) {
        carModelRepository.updateCarModel(carModel);
    }

    public void deleteCarModel(int id) {
        carModelRepository.deleteCarModel(id);
    }
}
