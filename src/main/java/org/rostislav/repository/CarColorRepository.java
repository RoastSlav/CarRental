package org.rostislav.repository;

import org.apache.ibatis.annotations.*;
import org.rostislav.models.CarColor;

import java.util.Collection;

@Mapper
public interface CarColorRepository {
    @Select("SELECT * FROM car_colors WHERE id = #{id}")
    CarColor getColorById(int id);

    @Select("SELECT * FROM car_colors")
    Collection<CarColor> getAllColors();

    @Insert("INSERT INTO car_colors (color_name) VALUES (#{carColor})")
    void addCarColor(CarColor carColor);

    @Update("UPDATE car_colors SET color_name=#{carColor} WHERE id=#{id}")
    void updateCarColor(CarColor carColor);

    @Delete("DELETE FROM car_colors WHERE id = #{id}")
    void deleteCarColor(int id);
}
