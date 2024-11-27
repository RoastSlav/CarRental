package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.Rental;

@Mapper
public interface RentalRepository {
    @Select("SELECT * FROM rentals WHERE id = #{id}")
    Rental getRentalById(int id);

    @Select("SELECT * FROM rentals")
    Collection<Rental> getAllRentals();

    @Insert("INSERT INTO rentals (user_id, car_id, pickup_date, dropoff_date, total_price, status) VALUES (#{userId}, #{carId}, #{pickupDate}, #{dropoffDate}, #{totalPrice}, #{status})")
    void addRental(Rental rental);

    @Update("UPDATE rentals SET user_id=#{userId}, car_id=#{carId}, pickup_date=#{pickupDate}, dropoff_date=#{dropoffDate}, total_price=#{totalPrice}, status=#{status} WHERE id=#{id}")
    void updateRental(Rental rental);

    @Delete("DELETE FROM rentals WHERE id = #{id}")
    void deleteRental(int id);

    @Update("UPDATE rentals SET status=#{status} WHERE id=#{id}")
    void updateRentalStatus(int id, String status);
}