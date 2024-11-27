package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.Rate;

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