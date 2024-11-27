package org.rostislav.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private int id;
    private int rentalId;
    private BigDecimal amount;
    private LocalDate date;
    private String method;
    private String status;

    public Payment() {
    }

    public Payment(int rentalId, BigDecimal amount, LocalDate date, String method, String status) {
        this.rentalId = rentalId;
        this.amount = amount;
        this.date = date;
        this.method = method;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
