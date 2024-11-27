package org.rostislav.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalWithNames {
    private int id;
    private String userName;
    private String carLicensePlate;
    private LocalDate pickupDate;
    private LocalDate dropoffDate;
    private BigDecimal totalPrice;
    private String status;

    public RentalWithNames() {
    }

    public RentalWithNames(int id, String userName, String carLicensePlate, LocalDate pickupDate, LocalDate dropoffDate, BigDecimal totalPrice, String status) {
        this.id = id;
        this.userName = userName;
        this.carLicensePlate = carLicensePlate;
        this.pickupDate = pickupDate;
        this.dropoffDate = dropoffDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCarLicensePlate() {
        return carLicensePlate;
    }

    public void setCarLicensePlate(String carLicensePlate) {
        this.carLicensePlate = carLicensePlate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getDropoffDate() {
        return dropoffDate;
    }

    public void setDropoffDate(LocalDate dropoffDate) {
        this.dropoffDate = dropoffDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return carLicensePlate + " - " + userName;
    }
}
