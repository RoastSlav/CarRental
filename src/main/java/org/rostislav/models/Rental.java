package org.rostislav.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Rental {
    private int id;
    private int userId;
    private int carId;
    private LocalDate pickupDate;
    private LocalDate dropoffDate;
    private BigDecimal totalPrice;
    private String status;

    public Rental(int id, int userId, int carId, LocalDate pickupDate, LocalDate dropoffDate, BigDecimal totalPrice, String status) {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.pickupDate = pickupDate;
        this.dropoffDate = dropoffDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Rental(int userId, int carId, LocalDate pickupDate, LocalDate dropoffDate, BigDecimal totalPrice, String status) {
        this.userId = userId;
        this.carId = carId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
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
}
