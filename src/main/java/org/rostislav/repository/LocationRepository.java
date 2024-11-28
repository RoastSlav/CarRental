package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.Location;
import org.rostislav.models.LocationWithCity;

@Mapper
public interface LocationRepository {
    @Select("SELECT * FROM locations")
    Collection<Location> getAllLocations();

    @Insert("INSERT INTO locations (name, address, city_id, zip_code) VALUES (#{name}, #{address}, #{cityId}, #{zipCode})")
    void addLocation(Location location);

    @Update("UPDATE locations SET name = #{name}, address = #{address}, city_id = #{cityId}, zip_code = #{zipCode} WHERE id = #{id}")
    void updateLocation(Location location);

    @Delete("DELETE FROM locations WHERE id = #{id}")
    void deleteLocation(int id);

    @Select("SELECT l.id, l.name, l.address, l.city_id, l.zip_code, c.city_name " +
            "FROM locations l " +
            "JOIN cities c ON l.city_id = c.id")
    Collection<LocationWithCity> getAllLocationsWithCityNames();
}