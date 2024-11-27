package org.rostislav.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import org.rostislav.models.Payment;
import org.rostislav.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

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
