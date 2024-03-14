package org.rostislav.repository;

import org.apache.ibatis.annotations.*;
import org.rostislav.models.CarModel;

import java.util.Collection;

@Mapper
public interface CarModelRepository {
    @Select("SELECT * FROM car_models WHERE id = #{id}")
    CarModel getCarModelById(int id);

    @Select("SELECT * FROM car_models")
    Collection<CarModel> getAllCarModels();

    @Insert("INSERT INTO car_models (model_name, make_id) VALUES (#{modelName}, #{makeId})")
    void addCarModel(CarModel carModel);

    @Update("UPDATE car_models SET model_name=#{modelName}, make_id=#{makeId} WHERE id=#{id}")
    void updateCarModel(CarModel carModel);

    @Delete("DELETE FROM car_models WHERE id = #{id}")
    void deleteCarModel(int id);
}