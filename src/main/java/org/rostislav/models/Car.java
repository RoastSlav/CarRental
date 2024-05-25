package org.rostislav.models;

public class Car {
    private int id;
    private int modelId;
    private int year;
    private int colorId;
    private String licensePlate;
    private String status;
    private int locationId;

    public Car() {
    }

    public Car(int modelId, int year, int colorId, String licensePlate, String status, int locationId) {
        this.modelId = modelId;
        this.year = year;
        this.colorId = colorId;
        this.licensePlate = licensePlate;
        this.status = status;
        this.locationId = locationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int model) {
        this.modelId = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return id + " - " + licensePlate + " - " + status;
    }
}
