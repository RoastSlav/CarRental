package org.rostislav.repository;

import org.apache.ibatis.annotations.*;
import org.rostislav.models.City;

import java.util.Collection;

@Mapper
public interface CityRepository {
    @Select("SELECT * FROM cities WHERE id = #{id}")
    City getCityById(int id);

    @Select("SELECT * FROM cities")
    Collection<City> getAllCities();

    @Insert("INSERT INTO cities (city_name, state_id) VALUES (#{cityName}, #{stateId})")
    void addCity(City city);

    @Update("UPDATE cities SET city_name=#{cityName}, state_id=#{stateId} WHERE id=#{id}")
    void updateCity(City city);

    @Delete("DELETE FROM cities WHERE id = #{id}")
    void deleteCity(int id);
}