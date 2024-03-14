package org.rostislav.repository;

import org.apache.ibatis.annotations.*;
import org.rostislav.models.Rate;

import java.util.Collection;

@Mapper
public interface RateRepository {
    @Select("SELECT * FROM rates WHERE id = #{id}")
    Rate getRateById(int id);

    @Select("SELECT * FROM rates")
    Collection<Rate> getAllRates();

    @Insert("INSERT INTO rates (car_id, daily_rate, weekly_rate, monthly_rate, extra_fees) VALUES (#{carId}, #{dailyRate}, #{weeklyRate}, #{monthlyRate}, #{extraFees})")
    void addRate(Rate rate);

    @Update("UPDATE rates SET car_id=#{carId}, daily_rate=#{dailyRate}, weekly_rate=#{weeklyRate}, monthly_rate=#{monthlyRate}, extra_fees=#{extraFees} WHERE id=#{id}")
    void updateRate(Rate rate);

    @Delete("DELETE FROM rates WHERE id = #{id}")
    void deleteRate(int id);
}