package org.rostislav.service;

import java.time.LocalDate;
import java.util.Collection;

import org.rostislav.models.PaymentReport;
import org.rostislav.repository.PaymentReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentReportService {
    @Autowired
    private PaymentReportRepository paymentReportRepository;

    public Collection<PaymentReport> getPaymentReport(String userName, String method, String status, LocalDate start, LocalDate end) {
        return paymentReportRepository.getPaymentReport(userName, method, status, start, end);
    }
}
