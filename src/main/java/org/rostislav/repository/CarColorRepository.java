package org.rostislav.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.rostislav.models.CarColor;

@Mapper
public interface CarColorRepository {
    @Select("SELECT * FROM car_colors WHERE id = #{id}")
    CarColor getColorById(int id);

    @Insert("INSERT INTO car_colors (color_name) VALUES (#{carColor})")
    void addCarColor(CarColor carColor);
}
