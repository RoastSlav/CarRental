package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.Location;
import org.rostislav.models.LocationWithCity;

@Mapper
public interface LocationRepository {
    @Select("SELECT * FROM locations")
    Collection<Location> getAllLocations();

    @Select("SELECT id, name, address, city_id AS cityId, zip_code AS zipCode FROM locations WHERE id = #{id}")
    Location getLocationById(int id);

    @Insert("INSERT INTO locations (name, address, city_id, zip_code) VALUES (#{name}, #{address}, #{cityId}, #{zipCode})")
    void addLocation(Location location);

    @Update("UPDATE locations SET name = #{name}, address = #{address}, city_id = #{cityId}, zip_code = #{zipCode} WHERE id = #{id}")
    void updateLocation(Location location);

    @Delete("DELETE FROM locations WHERE id = #{id}")
    void deleteLocation(int id);

    @Select("SELECT l.id, l.name, l.address, l.city_id, l.zip_code, c.city_name " +
            "FROM locations l " +
            "JOIN cities c ON l.city_id = c.id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "address", column = "address"),
            @Result(property = "cityId", column = "city_id"),
            @Result(property = "zipCode", column = "zip_code"),
            @Result(property = "cityName", column = "city_name")
    })
    Collection<LocationWithCity> getAllLocationsWithCityNames();
}