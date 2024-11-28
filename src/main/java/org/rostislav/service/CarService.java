package org.rostislav.service;

import java.util.Collection;

import org.rostislav.models.Car;
import org.rostislav.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public Collection<Car> getAllCars() {
        return carRepository.getAllCars();
    }

    public void updateCar(Car car) {
        carRepository.updateCar(car);
    }

    public void removeCar(int id) {
        carRepository.deleteCar(id);
    }

    public Car getCarById(int id) {
        return carRepository.getCarById(id);
    }

    public void addCar(int modelId, int year, int colorId, String licensePlate, String status, int locationId) {
        Car car = new Car();
        car.setModelId(modelId);
        car.setYear(year);
        car.setColorId(colorId);
        car.setLicensePlate(licensePlate);
        car.setStatus(status);
        car.setLocationId(locationId);

        carRepository.addCar(car);
    }
}
