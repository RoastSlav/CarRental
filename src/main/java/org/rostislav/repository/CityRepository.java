package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.City;
import org.rostislav.models.CityWithState;

@Mapper
public interface CityRepository {
    @Select("SELECT * FROM cities WHERE id = #{id}")
    City getCityById(int id);

    @Select("SELECT id, city_name AS cityName, state_id AS stateId FROM cities")
    Collection<City> getAllCities();

    @Insert("INSERT INTO cities (city_name, state_id) VALUES (#{cityName}, #{stateId})")
    void addCity(City city);

    @Update("UPDATE cities SET city_name=#{cityName}, state_id=#{stateId} WHERE id=#{id}")
    void updateCity(City city);

    @Delete("DELETE FROM cities WHERE id = #{id}")
    void deleteCity(int id);

    @Select("SELECT c.id, c.city_name, c.state_id, s.state_name " +
            "FROM cities c " +
            "JOIN states s ON c.state_id = s.id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "cityName", column = "city_name"),
            @Result(property = "stateId", column = "state_id"),
            @Result(property = "stateName", column = "state_name")
    })
    Collection<CityWithState> getAllCitiesWithStateNames();
}