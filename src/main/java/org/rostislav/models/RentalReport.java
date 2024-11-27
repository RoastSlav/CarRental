package org.rostislav.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalReport {
    private String userName;
    private String carLicensePlate;
    private LocalDate pickupDate;
    private LocalDate dropoffDate;
    private BigDecimal totalPrice;
    private String status;

    public RentalReport(String userName, String carLicensePlate, LocalDate pickupDate, LocalDate dropoffDate, BigDecimal totalPrice, String status) {
        this.userName = userName;
        this.carLicensePlate = carLicensePlate;
        this.pickupDate = pickupDate;
        this.dropoffDate = dropoffDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public String getCarLicensePlate() {
        return carLicensePlate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public LocalDate getDropoffDate() {
        return dropoffDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }
}
