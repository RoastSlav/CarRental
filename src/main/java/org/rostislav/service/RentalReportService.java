package org.rostislav.service;

import java.time.LocalDate;
import java.util.Collection;

import org.rostislav.models.RentalReport;
import org.rostislav.repository.RentalReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalReportService {
    @Autowired
    private RentalReportRepository rentalReportRepository;

    public Collection<RentalReport> getRentalReport(String userName, String carLicensePlate, String status, LocalDate startDate, LocalDate endDate) {
        return rentalReportRepository.getRentalReport(userName, carLicensePlate, status, startDate, endDate);
    }
}
