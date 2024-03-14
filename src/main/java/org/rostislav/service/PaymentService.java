package org.rostislav.service;

import org.rostislav.models.Payment;
import org.rostislav.models.Rate;
import org.rostislav.repository.PaymentRepository;
import org.rostislav.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RateRepository rateRepository;

    public void addRate(Rate rate) {
        rateRepository.addRate(rate);
    }

    public void addRate(BigDecimal dailyRate, BigDecimal weeklyRate, BigDecimal monthlyRate, BigDecimal extraFees) {
        rateRepository.addRate(new Rate(dailyRate, weeklyRate, monthlyRate, extraFees));
    }

    public Rate getRateById(int id) {
        return rateRepository.getRateById(id);
    }

    public Collection<Rate> getAllRates() {
        return rateRepository.getAllRates();
    }

    public void removeRate(int id) {
        rateRepository.deleteRate(id);
    }

    public void updateRate(Rate rate) {
        rateRepository.updateRate(rate);
    }

    public void addPayment(Payment payment) {
        paymentRepository.addPayment(payment);
    }

    public void addPayment(int rentalId, BigDecimal amount, LocalDate date, String method, String status) {
        paymentRepository.addPayment(new Payment(rentalId, amount, date, method, status));
    }

    public Payment getPaymentById(int id) {
        return paymentRepository.getPaymentById(id);
    }

    public Collection<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    public void removePayment(int id) {
        paymentRepository.deletePayment(id);
    }

    public void updatePayment(Payment payment) {
        paymentRepository.updatePayment(payment);
    }
}
