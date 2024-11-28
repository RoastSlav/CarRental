package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.CarModel;

@Mapper
public interface CarModelRepository {
    @Select("SELECT * FROM car_models")
    Collection<CarModel> getAllCarModels();

    @Insert("INSERT INTO car_models (model_name, make_id) VALUES (#{modelName}, #{makeId})")
    void addCarModel(CarModel carModel);

    @Update("UPDATE car_models SET model_name=#{modelName}, make_id=#{makeId} WHERE id=#{id}")
    void updateCarModel(CarModel carModel);

    @Delete("DELETE FROM car_models WHERE id = #{id}")
    void deleteCarModel(int id);
}