package org.rostislav.repository;

import org.apache.ibatis.annotations.*;
import org.rostislav.models.Location;

import java.util.Collection;

@Mapper
public interface LocationRepository {
    @Select("SELECT * FROM locations WHERE id = #{id}")
    Location getLocationById(int id);

    @Select("SELECT * FROM locations")
    Collection<Location> getAllLocations();

    @Insert("INSERT INTO locations (name, address, city_id) VALUES (#{name}, #{address}, #{cityId})")
    void addLocation(Location location);

    @Update("UPDATE locations SET name=#{name}, address=#{address}, city_id=#{cityId} WHERE id=#{id}")
    void updateLocation(Location location);

    @Delete("DELETE FROM locations WHERE id = #{id}")
    void deleteLocation(int id);
}