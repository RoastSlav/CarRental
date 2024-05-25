package org.rostislav.service;

import org.rostislav.models.Car;
import org.rostislav.models.CarColor;
import org.rostislav.models.CarMake;
import org.rostislav.models.CarModel;
import org.rostislav.repository.CarColorRepository;
import org.rostislav.repository.CarMakeRepository;
import org.rostislav.repository.CarModelRepository;
import org.rostislav.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class CarService {
    @Autowired
    private CarColorRepository carColorRepository;
    @Autowired
    private CarMakeRepository carMakeRepository;
    @Autowired
    private CarModelRepository carModelRepository;
    @Autowired
    private CarRepository carRepository;


    public void addColor(String colorName) {
        carColorRepository.addCarColor(new CarColor(colorName));
    }

    public CarColor getColorById(int id) {
        return carColorRepository.getColorById(id);
    }

    public Collection<CarColor> getAllColors() {
        return carColorRepository.getAllColors();
    }

    public void removeColor(int id) {
        carColorRepository.deleteCarColor(id);
    }

    public void updateColor(CarColor color) {
        carColorRepository.updateCarColor(color);
    }

    public void addCarMake(String makeName) {
        carMakeRepository.addCarMake(new CarMake(makeName));
    }

    public CarMake getCarMakeById(int id) {
        return carMakeRepository.getCarMakeById(id);
    }

    public Collection<CarMake> getAllCarMakes() {
        return carMakeRepository.getAllCarMakes();
    }

    public void removeCarMake(int id) {
        carMakeRepository.deleteCarMake(id);
    }

    public void updateCarMake(CarMake make) {
        carMakeRepository.updateCarMake(make);
    }

    public void addCarModel(String modelName, int makeId) {
        carModelRepository.addCarModel(new CarModel(modelName, makeId));
    }

    public void removeCarModel(int id) {
        carModelRepository.deleteCarModel(id);
    }

    public void updateCarModel(CarModel model) {
        carModelRepository.updateCarModel(model);
    }

    public CarModel getCarModelById(int id) {
        return carModelRepository.getCarModelById(id);
    }

    public Collection<CarModel> getAllCarModels() {
        return carModelRepository.getAllCarModels();
    }

    public void addCar(int modelId, int year, int colorId, String licensePlate, String status, int locationId) {
        carRepository.addCar(new Car(modelId, year, colorId, licensePlate, status, locationId));
    }

    public void addCar(Car car) {
        carRepository.addCar(car);
    }

    public void removeCar(int id) {
        carRepository.deleteCar(id);
    }

    public void updateCar(Car car) {
        carRepository.updateCar(car);
    }

    public Car getCarById(int id) {
        return carRepository.getCarById(id);
    }

    public Collection<Car> getAllCars() {
        return carRepository.getAllCars();
    }

    public void updateCarStatus(int id, String status) {
        Car car = carRepository.getCarById(id);
        car.setStatus(status);
        carRepository.updateCar(car);
    }

    public void updateCarLocation(int id, int locationId) {
        Car car = carRepository.getCarById(id);
        car.setLocationId(locationId);
        carRepository.updateCar(car);
    }
}
