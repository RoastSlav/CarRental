package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.Rental;
import org.rostislav.models.RentalWithNames;

@Mapper
public interface RentalRepository {
    @Select("SELECT * FROM rentals WHERE id = #{id}")
    Rental getRentalById(int id);

    @Select("SELECT * FROM rentals")
    Collection<Rental> getAllRentals();

    @Select("SELECT r.id, u.full_name AS userName, c.license_plate AS carLicensePlate, r.pickup_date AS pickupDate, r.dropoff_date AS dropoffDate, r.total_price AS totalPrice, r.status " +
            "FROM rentals r " +
            "JOIN users u ON r.user_id = u.id " +
            "JOIN cars c ON r.car_id = c.id")
    Collection<RentalWithNames> getAllRentalsWithNames();

    @Insert("INSERT INTO rentals (user_id, car_id, pickup_date, dropoff_date, total_price, status) VALUES (#{userId}, #{carId}, #{pickupDate}, #{dropoffDate}, #{totalPrice}, #{status})")
    void addRental(Rental rental);

    @Update("UPDATE rentals SET user_id=#{userId}, car_id=#{carId}, pickup_date=#{pickupDate}, dropoff_date=#{dropoffDate}, total_price=#{totalPrice}, status=#{status} WHERE id=#{id}")
    void updateRental(Rental rental);

    @Delete("DELETE FROM rentals WHERE id = #{id}")
    void deleteRental(int id);

    @Update("UPDATE rentals SET status=#{status} WHERE id=#{id}")
    void updateRentalStatus(int id, String status);

    @Select("SELECT r.id, u.full_name AS userName, c.license_plate AS carLicensePlate, r.pickup_date AS pickupDate, r.dropoff_date AS dropoffDate, r.total_price AS totalPrice, r.status " +
            "FROM rentals r " +
            "JOIN users u ON r.user_id = u.id " +
            "JOIN cars c ON r.car_id = c.id " +
            "WHERE r.id = #{rentalId}")
    RentalWithNames getRentalWithNames(int rentalId);
}
