package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.Car;

@Mapper
public interface CarRepository {

    @Select("SELECT c.*, col.color_name, loc.name AS location_name, m.model_name, mk.make_name " +
            "FROM cars c " +
            "JOIN car_colors col ON c.color_id = col.id " +
            "JOIN locations loc ON c.location_id = loc.id " +
            "JOIN car_models m ON c.model_id = m.id " +
            "JOIN car_makes mk ON m.make_id = mk.id")
    Collection<Car> getAllCars();

    @Select("SELECT * FROM cars WHERE id = #{id}")
    Car getCarById(int id);

    @Insert("INSERT INTO cars (model_id, year, color_id, license_plate, status, location_id) VALUES (#{modelId}, #{year}, #{colorId}, #{licensePlate}, #{status}, #{locationId})")
    void addCar(Car car);

    @Update("UPDATE cars SET model_id=#{modelId}, year=#{year}, color_id=#{colorId}, license_plate=#{licensePlate}, status=#{status}, location_id=#{locationId} WHERE id=#{id}")
    void updateCar(Car car);

    @Delete("DELETE FROM cars WHERE id = #{id}")
    void deleteCar(int id);
}
