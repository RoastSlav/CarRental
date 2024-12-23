package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.CarMake;

@Mapper
public interface CarMakeRepository {
    @Select("SELECT * FROM car_makes WHERE id = #{id}")
    CarMake getCarMakeById(int id);

    @Select("SELECT * FROM car_makes")
    Collection<CarMake> getAllCarMakes();

    @Insert("INSERT INTO car_makes (make_name) VALUES (#{makeName})")
    void addCarMake(CarMake carMake);

    @Update("UPDATE car_makes SET make_name=#{makeName} WHERE id=#{id}")
    void updateCarMake(CarMake carMake);

    @Delete("DELETE FROM car_makes WHERE id = #{id}")
    void deleteCarMake(int id);
}