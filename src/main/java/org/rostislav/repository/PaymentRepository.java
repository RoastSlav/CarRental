package org.rostislav.repository;

import org.apache.ibatis.annotations.*;
import org.rostislav.models.Payment;

import java.util.Collection;

@Mapper
public interface PaymentRepository {
    @Select("SELECT * FROM payments WHERE id = #{id}")
    Payment getPaymentById(int id);

    @Select("SELECT * FROM payments")
    Collection<Payment> getAllPayments();

    @Insert("INSERT INTO payments (rental_id, amount, date, method, status) VALUES (#{rentalId}, #{amount}, #{date}, #{method}, #{status})")
    void addPayment(Payment payment);

    @Update("UPDATE payments SET rental_id=#{rentalId}, amount=#{amount}, date=#{date}, method=#{method}, status=#{status} WHERE id=#{id}")
    void updatePayment(Payment payment);

    @Delete("DELETE FROM payments WHERE id = #{id}")
    void deletePayment(int id);
}