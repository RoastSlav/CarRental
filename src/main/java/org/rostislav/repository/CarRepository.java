package org.rostislav.repository;

import org.apache.ibatis.annotations.*;
import org.rostislav.models.Car;

import java.util.Collection;

@Mapper
public interface CarRepository {
    @Select("SELECT * FROM cars WHERE id = #{id}")
    Car getCarById(int id);

    @Select("SELECT * FROM cars")
    Collection<Car> getAllCars();

    @Insert("INSERT INTO cars (model_id, year, color_id, license_plate, status, location_id) VALUES (#{modelId}, #{year}, #{colorId}, #{licensePlate}, #{status}, #{locationId})")
    void addCar(Car car);

    @Update("UPDATE cars SET model_id=#{modelId}, year=#{year}, color_id=#{colorId}, license_plate=#{licensePlate}, status=#{status}, location_id=#{locationId} WHERE id=#{id}")
    void updateCar(Car car);

    @Delete("DELETE FROM cars WHERE id = #{id}")
    void deleteCar(int id);
}