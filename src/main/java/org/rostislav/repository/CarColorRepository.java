package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.CarColor;

@Mapper
public interface CarColorRepository {
    @Select("SELECT * FROM car_colors")
    Collection<CarColor> getAllColors();

    @Insert("INSERT INTO car_colors (color_name) VALUES (#{colorName})")
    void addCarColor(CarColor carColor);

    @Update("UPDATE car_colors SET color_name=#{colorName} WHERE id=#{id}")
    void updateCarColor(CarColor carColor);

    @Delete("DELETE FROM car_colors WHERE id = #{id}")
    void deleteCarColor(int id);
}
